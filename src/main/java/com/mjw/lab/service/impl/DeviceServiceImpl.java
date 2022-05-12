package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.mapper.DeviceMapper;
import com.mjw.lab.dao.mapper.ReservationFormMapper;
import com.mjw.lab.dao.parm.DeviceParm;
import com.mjw.lab.dao.pojo.Device;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.DeviceService;
import com.mjw.lab.utils.PermissionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    ReservationFormMapper reservationFormMapper;

    @Autowired
    PermissionUtils permissionUtils;

    @Override
    public Result getAllDevice(Map<String,String> date,Map<String,String> pageParm) {

        Page<Device> page = new Page<>(Integer.parseInt(pageParm.get("currentPage")), Integer.parseInt(pageParm.get("pageSize")));

        String beginTime = date.get("startTime");
        String endTime = date.get("endTime");

        if(!StringUtils.isAnyBlank(beginTime,endTime)){
            //将时间格式化
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date beginTime1 = null;
            Date endTime1= null ;
            try {
                beginTime1 = format.parse(beginTime);
                endTime1 = format.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ArrayList<Device> devices = deviceMapper.getAllDeviceByTime(page, beginTime1, endTime1);
            return Result.success(devices);
        }else{
            ArrayList<Device> devices = deviceMapper.getAllDevice(page);

            return Result.success(devices);
        }

 /*       if(!StringUtils.isAllBlank(beginTime,endTime)) {
            //将时间格式化
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date beginTime1 = null;
            Date endTime1= null ;
            try {
                beginTime1 = format.parse(beginTime);
                endTime1 = format.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //筛选所需时间内被占用的的设备的申请单
            LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
            if (!StringUtils.isAnyBlank(beginTime, endTime))
                wrapper.between(ReservationForm::getBeginTime, beginTime1, endTime1);
            else if (StringUtils.isBlank(beginTime) && !StringUtils.isBlank(endTime))
                wrapper.le(ReservationForm::getBeginTime, endTime1);
            else if (StringUtils.isBlank(endTime) && !StringUtils.isBlank(beginTime))
                wrapper.ge(ReservationForm::getBeginTime, beginTime1);
            List<ReservationForm> forms = reservationFormMapper.selectList(wrapper);


            //将时间内被占用的设备id提取出来并选出设备
            List<Long> ids = new ArrayList<>();
            LambdaQueryWrapper<Device> wrapper1 = new LambdaQueryWrapper<>();
            if(forms.size()!=0) {
                for (ReservationForm form : forms) {
                    ids.add(form.getDeviceId());
                }
                wrapper1.notIn(Device::getId, ids);
            }
            wrapper1.eq(Device::isExist,true);
            List<Device> devices = deviceMapper.selectList(wrapper1);

            Page<Device> devices1= deviceMapper.selectPage(page,wrapper1);

            return Result.success(devices1);

        }else {

            //没有填写时间的设备筛选
            LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Device::isExist,true);
            List<Device> devices = deviceMapper.selectList(wrapper);

            Page<Device> devices1= deviceMapper.selectPage(page,wrapper);
            return Result.success(devices1);
        }*/


    }



    @Override
    public Result saveDevice(DeviceParm devices) {
        if(permissionUtils.isNotAdm())
            return Result.fail(1005,"Insufficient permissions");

        if(StringUtils.isAnyBlank(devices.getDeviceType(),devices.getBuyTime().toString()
                                    ,devices.getManufacturer(),devices.getPurpose(),
                                    String.valueOf(devices.getPrice()))){
            return Result.fail(204,"All options cannot be empty");
        }

        for (int i = 0; i < devices.getNumber(); i++) {
            Device device = new Device();
            BeanUtils.copyProperties(devices,device);
            deviceMapper.insert(device);
        }
        return Result.success(devices);
    }

    @Override
    public Result updateDevice(Long id, DeviceParm device) {
        if(permissionUtils.isNotAdm())
            return Result.fail(1005,"Insufficient permissions");

        Device d = deviceMapper.selectById(id);
        BeanUtils.copyProperties(device,d);
        deviceMapper.updateById(d);

        return Result.success(d);
    }

    @Override
    public Result deleteDevice(Long id) {
        if(permissionUtils.isNotAdm())
            return Result.fail(1005,"Insufficient permissions");

        Device device = deviceMapper.selectById(id);
        device.setExist(false);

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getId,id);
        deviceMapper.update(device,wrapper);

        return Result.success(device);
    }

    @Override
    public Result getDevice(Long id) {

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::isExist,true);
        wrapper.eq(Device::getId,id);
        Device device = deviceMapper.selectOne(wrapper);

        return Result.success(device);
    }
}
