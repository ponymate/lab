package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Outsider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutsiderMapper extends BaseMapper<Outsider> {

    Page<Outsider> getAllOutsider(Page<Outsider> page);

    Page<Outsider> getAllOutsiderByName(Page<Outsider> outsiderPage, String name);
}
