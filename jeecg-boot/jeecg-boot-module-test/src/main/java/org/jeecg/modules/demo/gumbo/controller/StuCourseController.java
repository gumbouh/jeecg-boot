package org.jeecg.modules.demo.gumbo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.gumbo.entity.Course;
import org.jeecg.modules.demo.gumbo.entity.StuCourse;
import org.jeecg.modules.demo.gumbo.mapper.CourseMapper;
import org.jeecg.modules.demo.gumbo.mapper.StuCourseMapper;
import org.jeecg.modules.demo.gumbo.service.IStuCourseService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.gumbo.util.CommonUtil;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

import static org.jeecg.modules.demo.gumbo.util.CommonUtil.objectToMap;

/**
 * @Description: 学生选课表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Api(tags="学生选课表")
@RestController
@RequestMapping("/gumbo/stuCourse")
@Slf4j
public class StuCourseController extends JeecgController<StuCourse, IStuCourseService> {
	@Autowired
	private IStuCourseService stuCourseService;
	@Autowired
	private StuCourseMapper stuCourseMapper;
	@Autowired
	private ISysBaseAPI iSysBaseAPI;
	@Autowired
	private CourseMapper courseMapper;
	private CommonUtil commonUtil;
	/**
	 * 分页列表查询
	 *
	 * @param stuCourse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学生选课表-分页列表查询")
	@ApiOperation(value="学生选课表-分页列表查询", notes="学生选课表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StuCourse stuCourse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StuCourse> queryWrapper = QueryGenerator.initQueryWrapper(stuCourse, req.getParameterMap());
		Page<StuCourse> page = new Page<StuCourse>(pageNo, pageSize);
		IPage<StuCourse> pageList = stuCourseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param stuCourse
	 * @return
	 */
	@AutoLog(value = "学生选课表-添加")
	@ApiOperation(value="学生选课表-添加", notes="学生选课表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StuCourse stuCourse) {
		stuCourseService.save(stuCourse);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param stuCourse
	 * @return
	 */
	@AutoLog(value = "学生选课表-编辑")
	@ApiOperation(value="学生选课表-编辑", notes="学生选课表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StuCourse stuCourse) {
		stuCourseService.updateById(stuCourse);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生选课表-通过id删除")
	@ApiOperation(value="学生选课表-通过id删除", notes="学生选课表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		stuCourseService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生选课表-批量删除")
	@ApiOperation(value="学生选课表-批量删除", notes="学生选课表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.stuCourseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生选课表-通过id查询")
	@ApiOperation(value="学生选课表-通过id查询", notes="学生选课表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StuCourse stuCourse = stuCourseService.getById(id);
		if(stuCourse==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(stuCourse);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param stuCourse
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StuCourse stuCourse) {
        return super.exportXls(request, stuCourse, StuCourse.class, "学生选课表");
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
        return super.importExcel(request, response, StuCourse.class);
    }


	 /**
	  * 分页模糊查询
	  * @param stuCourse
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "学生选课表-分页模糊查询")
	 @ApiOperation(value="学生选课表-分页模糊查询", notes="学生选课表-分页模糊查询")
	 @PostMapping(value = "/fuzzyQuery")
	 public Result<?> fuzzyQuery(StuCourse stuCourse,
								 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize){
		 QueryWrapper<StuCourse> queryWrapper = new QueryWrapper<>();
		 queryWrapper.like(stuCourse.getCourseId()!=null,"course_id",stuCourse.getCourseId()).or()
				 	.like(stuCourse.getStudentId()!=null,"student_id",stuCourse.getCourseId()).eq("student_id",this.getId());
		 Page<StuCourse> page = new Page<StuCourse>(pageNo, pageSize);
		 IPage<StuCourse> pageList = stuCourseService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }



	 /**
	  * 分页列表查询当前学生选课
	  *
	  * @param stuCourse
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "学生选课表-查询当前学生选课")
	 @ApiOperation(value="学生选课表-分页列表查询当前学生选课", notes="学生选课表-分页列表查询当前学生选课")
	 @GetMapping(value = "/listByStuId")
	 public Result<?> queryPageListByStuId(StuCourse stuCourse,
										   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										   HttpServletRequest req) {
		 QueryWrapper<StuCourse> queryWrapper = new QueryWrapper<>();
		 QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
		 Page<StuCourse> page = new Page<StuCourse>(pageNo, pageSize);
		 Page<Course> page2 = new Page<Course>(pageNo, pageSize);
	 	 //获取当前用户的id
		 String id = this.getId();
		 //获取课程idList
		 queryWrapper.select("course_id").eq("student_id",id);
		 /*ArrayList<String> courseIdList = new ArrayList<>();*/
		 List<Object> coursesId = stuCourseMapper.selectObjs(queryWrapper);

		 /*for (Object courseIds: coursesId) {
			 courseIdList.add(courseIds.toString());
		 }
		 courseIdList.forEach(System.out::println);*/
		 //获取课程内容,visibleTag==1
		 courseQueryWrapper.eq("visible_tag",1).in("id",coursesId);
		 IPage<Course> courseIPage = courseMapper.selectPage(page2, courseQueryWrapper);
		 //List<Course> courses = courseMapper.selectBatchIds(courseIdList);
		 //IPage<StuCourse> pageList = stuCourseMapper.selectPage(page, queryWrapper);
		 return Result.OK(courseIPage);
	 }
	 /**
	  * 获取当前用户的id
	  * @return
	  */
	 public String getId(){
		 Object principal = SecurityUtils.getSubject().getPrincipal();
		 System.out.println(SecurityUtils.getSubject().getSession().toString());
		 System.out.println(principal.toString());
		 Map<String,Object> map = (Map<String, Object>) objectToMap(principal);
		 return map.get("id").toString();
	 }
}
