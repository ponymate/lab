package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Date;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    public ArrayList<Device> getAllDeviceByTime(Page<Device> page,Date startTime, Date endTime);

    public ArrayList<Device> getAllDevice(Page<Device> page);
}
