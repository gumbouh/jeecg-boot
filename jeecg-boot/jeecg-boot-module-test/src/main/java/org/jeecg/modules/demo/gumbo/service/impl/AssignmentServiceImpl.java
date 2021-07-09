package org.jeecg.modules.demo.gumbo.service.impl;

import org.jeecg.modules.demo.gumbo.entity.Assignment;
import org.jeecg.modules.demo.gumbo.mapper.AssignmentMapper;
import org.jeecg.modules.demo.gumbo.service.IAssignmentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 作业
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements IAssignmentService {

}
