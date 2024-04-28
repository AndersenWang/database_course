package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.entity.dto.Course;
import org.example.mapper.CourseMapper;
import org.example.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> getAllCourse() {
        return courseMapper.getAllCourse();
    }
}
