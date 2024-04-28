package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.RestBean;
import org.example.entity.dto.Course;
import org.example.entity.vo.response.CourseVO;
import org.example.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    CourseService courseService;
    @GetMapping("/all-course")
    public RestBean<List<CourseVO>> getAllCourse() {
        List<Course> list = courseService.getAllCourse();
        List<CourseVO> list1 = list.stream().map(new Function<Course, CourseVO>() {
            @Override
            public CourseVO apply(Course course) {
//                CourseVO courseVO = new CourseVO();
//                BeanUtils.copyProperties(course, courseVO);
                return course.asViewObject(CourseVO.class);
            }
        }).toList();
        return RestBean.success(list1);
    }
}
