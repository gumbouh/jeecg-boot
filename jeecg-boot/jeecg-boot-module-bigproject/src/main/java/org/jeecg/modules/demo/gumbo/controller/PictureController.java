package org.jeecg.modules.demo.gumbo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.converter.PicturesManager;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.gumbo.entity.Picture;
import org.jeecg.modules.demo.gumbo.mapper.PictureMapper;
import org.jeecg.modules.demo.gumbo.service.IPictureService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: picture
 * @Author: jeecg-boot
 * @Date:   2021-07-09
 * @Version: V1.0
 */
@Api(tags="picture")
@RestController
@RequestMapping("/gumbo/picture")
@Slf4j
public class PictureController extends JeecgController<Picture, IPictureService> {
	@Autowired
	private IPictureService pictureService;
	@Autowired
	private PictureMapper pictureMapper;
	/**
	 * ??????????????????
	 *
	 * @param picture
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "picture-??????????????????")
	@ApiOperation(value="picture-??????????????????", notes="picture-??????????????????")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Picture picture,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Picture> queryWrapper = QueryGenerator.initQueryWrapper(picture, req.getParameterMap());
		Page<Picture> page = new Page<Picture>(pageNo, pageSize);
		IPage<Picture> pageList = pictureService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   ??????
	 *
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "picture-??????")
	@ApiOperation(value="picture-??????", notes="picture-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Picture picture) {
		pictureService.save(picture);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "picture-??????")
	@ApiOperation(value="picture-??????", notes="picture-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Picture picture) {
		pictureService.updateById(picture);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "picture-??????id??????")
	@ApiOperation(value="picture-??????id??????", notes="picture-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pictureService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "picture-????????????")
	@ApiOperation(value="picture-????????????", notes="picture-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pictureService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "picture-??????id??????")
	@ApiOperation(value="picture-??????id??????", notes="picture-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Picture picture = pictureService.getById(id);
		if(picture==null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(picture);
	}

    /**
    * ??????excel
    *
    * @param request
    * @param picture
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Picture picture) {
        return super.exportXls(request, picture, Picture.class, "picture");
    }

    /**
      * ??????excel????????????
    *
    * @param request
    * @param response
    * @return
    */
	@AutoLog(value = "picture-??????excel????????????")
	@ApiOperation(value="picture-??????excel????????????", notes="picture-??????excel????????????")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Picture.class);
    }

	 /**
	  * ????????????????????????????????????
	  * @param isUsability
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "picture-??????????????????????????????")
	 @ApiOperation(value="picture-??????????????????????????????", notes="picture-??????????????????????????????")
	 @GetMapping(value = "/queryByUsability")
    public Result<?> queryByUsability(@RequestParam(name="usability",required=true) String isUsability,
									  @RequestParam(name="name",required=true) String name,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize){
    	int usability = 0;
    	//?????????????????????????????????0???1
		switch (isUsability){
			case "??????":
				usability = 1;
				break;
			case "??????":
				usability = -1;
				break;
		}
		QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name",name).eq("usability", usability);
		Page<Picture> page = new Page<Picture>(pageNo, pageSize);
		//???????????????????????????????????????,????????????
		if(usability==-1 && name==null){
			queryWrapper.or().gt("usability",usability);
		}else if(usability==-1 && name!=null){
			queryWrapper.or().like("name",name);
		}
		IPage<Picture> picturePage = pictureMapper.selectPage(page, queryWrapper);
		return Result.OK(picturePage);
	}
}
