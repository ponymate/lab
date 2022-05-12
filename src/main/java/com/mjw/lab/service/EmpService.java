package com.mjw.lab.service;

import com.mjw.lab.dao.pojo.DeviceAdm;
import com.mjw.lab.dao.vo.Result;


public interface EmpService {
    public Result getAllEmp();

    public Result saveEmp(DeviceAdm deviceAdm);

    public Result deleteEmp(Long id);

    public Result updateEmp(Long id,DeviceAdm deviceAdm);

    Result getEmp(Long id);

    DeviceAdm getDeviceAdmByIdAndPassword(String id, String password);
}
