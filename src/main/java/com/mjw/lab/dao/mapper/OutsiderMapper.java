package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Outsider;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OutsiderMapper extends BaseMapper<Outsider> {

    public List<Outsider> getAllOutsider(Page<Outsider> page);

}
