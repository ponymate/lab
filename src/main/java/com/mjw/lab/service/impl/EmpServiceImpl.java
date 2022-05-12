package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mjw.lab.dao.mapper.DeviceAdmMapper;
import com.mjw.lab.dao.pojo.DeviceAdm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.EmpService;
import com.mjw.lab.utils.JwtUtils;
import com.mjw.lab.utils.PermissionUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private DeviceAdmMapper deviceAdmMapper;

    @Autowired
    private PermissionUtils permissionUtils;

    @Override
    public Result getAllEmp() {
        if(permissionUtils.isNotBoss())
            return Result.fail(1005 ,"Insufficient permissions");

        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::isExist,true);
        List<DeviceAdm> deviceAdm = deviceAdmMapper.selectList(wrapper);
        return Result.success(deviceAdm);
    }

    @Override
    public Result saveEmp(DeviceAdm deviceAdm) {
        if(permissionUtils.isNotBoss())
            return Result.fail(1005 ,"Insufficient permissions");

        if(StringUtils.isAnyBlank(deviceAdm.getName(),deviceAdm.getPassword(),deviceAdm.getId().toString())){
            return Result.fail(204,"All options cannot be empty");
        }
        if(deviceAdmMapper.selectById(deviceAdm.getId())!=null||selectByName(deviceAdm.getName())!=null){
            return Result.fail(1002,"user has exists");
        }


        deviceAdm.setPassword(DigestUtils.md5Hex(deviceAdm.getPassword()));
        deviceAdmMapper.insert(deviceAdm);

        String token = JwtUtils.getJwtToken(deviceAdm.getId(),deviceAdm.getName(),"deviceAdm");

        return Result.success(token);
    }

    private DeviceAdm selectByName(String name) {

        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::getName,name);
        return deviceAdmMapper.selectOne(wrapper);
    }

    @Override
    public Result deleteEmp(Long id) {

        if(permissionUtils.isNotBoss())
            return Result.fail(1005 ,"Insufficient permissions");

        DeviceAdm d = deviceAdmMapper.selectById(id);
        d.setExist(false);

        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::getId,id);
        deviceAdmMapper.update(d,wrapper);
        return Result.success(d);
    }

    @Override
    public Result updateEmp(Long id,DeviceAdm deviceAdm) {
        if(permissionUtils.isNotBoss())
            return Result.fail(1005 ,"Insufficient permissions");

        DeviceAdm d = deviceAdmMapper.selectById(id);
        String password = deviceAdm.getPassword();
        deviceAdm.setPassword(DigestUtils.md5Hex(password));

        if(!StringUtils.isBlank(deviceAdm.getName())){
            if(selectByName(deviceAdm.getName())!=null)
                return Result.fail(1002 ,"user has exists");
        }

        BeanUtils.copyProperties(deviceAdm,d);
        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::getId,id);
        deviceAdmMapper.update(d,wrapper);

        d = deviceAdmMapper.selectById(id);
        return Result.success(d);

    }

    @Override
    public Result getEmp(Long id) {
        if(permissionUtils.isNotBoss())
            return Result.fail(1005 ,"Insufficient permissions");

        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::isExist,true);
        wrapper.eq(DeviceAdm::getId,id);
        return Result.success(deviceAdmMapper.selectOne(wrapper));
    }


    @Override
    public DeviceAdm getDeviceAdmByIdAndPassword(String id, String password) {

        LambdaQueryWrapper<DeviceAdm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceAdm::getId,id);
        wrapper.eq(DeviceAdm::getPassword,password);
        return deviceAdmMapper.selectOne(wrapper);
    }

}
