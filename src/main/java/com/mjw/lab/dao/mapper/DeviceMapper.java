package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    Page<Device> getAllDeviceByTime(Page<Device> page,Date startTime, Date endTime);

    Page<Device> getAllDevice(Page<Device> page);

    Page<Device> getAllDeviceByTimeAndName(Page<Device> page, Date beginTime1, Date endTime1, String name);

    Page<Device> getAllDeviceByName(Page<Device> page, String name);
}
