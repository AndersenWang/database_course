package org.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.reflection.BaseData;

@Data
@TableName("course")
@AllArgsConstructor
@NoArgsConstructor
public class Course implements BaseData {
    @TableId(type = IdType.INPUT)
    private String cid;
    @TableField("course_name")
    private String course_name;
    @TableField("credit")
    private int credit;
    @TableField("course_last_time")
    private int course_last_time;
    @TableField("major_id")
    private String major_id;
}
