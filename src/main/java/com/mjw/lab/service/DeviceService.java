package com.mjw.lab.service;

import com.mjw.lab.dao.parm.DeviceParm;
import com.mjw.lab.dao.vo.Result;

import java.util.Map;

public interface DeviceService {
    Result getAllDevice(Map<String,String> date,Map<String,String> parm);

    Result saveDevice(DeviceParm devices);

    Result updateDevice(Long id, DeviceParm device);

    Result deleteDevice(Long id);

    Result getDevice(Long id);
}
