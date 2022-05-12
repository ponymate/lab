package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    public List<Teacher> getAllTeacher(Page<Teacher> page);
}
