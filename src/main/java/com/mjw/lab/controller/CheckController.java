package com.mjw.lab.controller;


import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 10:37 2022/4/7
 * @Modified By:
 */


@CrossOrigin
@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @PutMapping("/teacherCheck/{id}/{pass}")
    public Result teacherCheck(@PathVariable Long id, @PathVariable String pass){
        return checkService.teacherCheck(id,pass);
    }

    @PutMapping("/deviceAdmCheck/{id}/{pass}")
    public Result deviceAdmCheck(@PathVariable Long id,@PathVariable String pass){
        return checkService.deviceAdmCheck(id,pass);
    }

    @PutMapping("/superAdmCheck/{id}/{pass}")
    public Result superAdmCheck(@PathVariable Long id,@PathVariable String pass){
        return checkService.superAdmCheck(id,pass);
    }

}
