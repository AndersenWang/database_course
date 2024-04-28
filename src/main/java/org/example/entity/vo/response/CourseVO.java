package org.example.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    private String cid;
    private String course_name;
    private int credit;
    private int course_last_time;
    private String major_id;
}