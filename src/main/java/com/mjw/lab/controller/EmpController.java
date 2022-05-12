package com.mjw.lab.controller;

import com.mjw.lab.dao.pojo.DeviceAdm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 设备管理员的信息维护
 */

@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result getAllEmp(){
        return empService.getAllEmp();
    }

    @PostMapping
    public Result saveEmp(@RequestBody DeviceAdm deviceAdm){
        return empService.saveEmp(deviceAdm);
    }

    @DeleteMapping("{id}")
    public Result deleteEmp(@PathVariable("id") Long id ){
        return empService.deleteEmp(id);
    }

    @PutMapping("{id}")
    public Result updateEmp(@PathVariable Long id,@RequestBody DeviceAdm deviceAdm){
        return empService.updateEmp(id,deviceAdm);
    }

    @GetMapping("/{id}")
    public Result getEmp(@PathVariable Long id){
        return empService.getEmp(id);
    }

}
