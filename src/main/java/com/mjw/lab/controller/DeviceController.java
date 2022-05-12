package com.mjw.lab.controller;

import com.mjw.lab.dao.parm.DeviceParm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 实验设备的增删改查
 */

@CrossOrigin
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Result getAllDevice(@RequestParam(required = false) Map<String,String> date,@RequestParam Map<String,String> pageParm){

        return deviceService.getAllDevice(date,pageParm);
    }

    @PostMapping
    public Result saveDeviceBatch(@RequestBody DeviceParm devices){

        return deviceService.saveDevice(devices);
    }

    @PutMapping("/{id}")
    public Result updateDevice(@PathVariable Long id, @RequestBody DeviceParm device){

        return deviceService.updateDevice(id,device);
    }

    @DeleteMapping("/{id}")
    public Result deleteDevice(@PathVariable Long id){

        return deviceService.deleteDevice(id);
    }

    @GetMapping("/{id}")
    public Result getDevice(@PathVariable Long id){

        return deviceService.getDevice(id);
    }


}
