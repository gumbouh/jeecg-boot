package org.jeecg.modules.demo.gumbo.service.impl;

import org.jeecg.modules.demo.gumbo.entity.Course;
import org.jeecg.modules.demo.gumbo.mapper.CourseMapper;
import org.jeecg.modules.demo.gumbo.service.ICourseService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 课程
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
