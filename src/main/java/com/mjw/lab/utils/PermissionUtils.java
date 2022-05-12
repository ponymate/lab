package com.mjw.lab.utils;

import com.mjw.lab.dao.mapper.TeaStuMapper;
import com.mjw.lab.dao.pojo.DeviceAdm;
import com.mjw.lab.dao.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 16:35 2022/4/12
 * @Modified By:
 */
@Component
public class PermissionUtils {

    @Autowired
    TeaStuMapper teaStuMapper;

    public boolean isNotSelf(Long id){
        return UserThreadLocal.get() == null || !UserThreadLocal.get().getId().equals(id);
    }

    public boolean isNotAdm(){
        return AdmThreadLocal.get()==null;
    }

    public boolean isNotTeacher(Long id){
        Teacher teacher = teaStuMapper.getTeaByStuId(UserThreadLocal.get().getId(),id);
        return teacher==null;
    }

    public boolean isNotBoss(){
        DeviceAdm deviceAdm = AdmThreadLocal.get();
        return AdmThreadLocal.get()==null||!AdmThreadLocal.get().isAdm();
    }

    public boolean isNotOutsider(){
        return !UserThreadLocal.get().getType().equals("outsider");
    }

}
