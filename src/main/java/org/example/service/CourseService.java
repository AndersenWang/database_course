package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.dto.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    List<Course> getAllCourse();

}
