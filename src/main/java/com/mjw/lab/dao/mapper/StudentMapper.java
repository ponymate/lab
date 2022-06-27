package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @MapKey("major")
    List<Map<String, Integer>> count();

    Page<Student> getAllStudent(Page<Student> page);

    Page<Student> getTeacherStudent(Page<Student> page,Long teacherId);

    Page<Student> getTeacherStudentByName(Page<Student> studentPage, Long id, String name);

    Page<Student> getAllStudentByName(Page<Student> studentPage, String name);
}
