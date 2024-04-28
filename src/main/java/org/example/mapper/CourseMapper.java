package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.dto.Course;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("select * from course")
    List<Course> getAllCourse();
}
