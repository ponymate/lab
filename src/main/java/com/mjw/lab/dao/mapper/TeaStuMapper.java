package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mjw.lab.dao.pojo.TeaStu;
import com.mjw.lab.dao.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 19:24 2022/4/30
 * @Modified By:
 */
@Mapper
public interface TeaStuMapper extends BaseMapper<TeaStu> {

    public Teacher getTeaByStuId(Long teacherId,Long studentId);

}
