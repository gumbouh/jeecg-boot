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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.gumbo.entity.Picture;
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
	
	/**
	 * 分页列表查询
	 *
	 * @param picture
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "picture-分页列表查询")
	@ApiOperation(value="picture-分页列表查询", notes="picture-分页列表查询")
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
	 *   添加
	 *
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "picture-添加")
	@ApiOperation(value="picture-添加", notes="picture-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Picture picture) {
		pictureService.save(picture);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "picture-编辑")
	@ApiOperation(value="picture-编辑", notes="picture-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Picture picture) {
		pictureService.updateById(picture);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "picture-通过id删除")
	@ApiOperation(value="picture-通过id删除", notes="picture-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pictureService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "picture-批量删除")
	@ApiOperation(value="picture-批量删除", notes="picture-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pictureService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "picture-通过id查询")
	@ApiOperation(value="picture-通过id查询", notes="picture-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Picture picture = pictureService.getById(id);
		if(picture==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(picture);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param picture
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Picture picture) {
        return super.exportXls(request, picture, Picture.class, "picture");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Picture.class);
    }

}
