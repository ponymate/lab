package com.mjw.lab.controller;

import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.SelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 借用人员维护个人信息。老师批量注册学生信息
 */

@CrossOrigin
@RestController
@RequestMapping("/borrower")
@Slf4j
public class SelfController {

    @Autowired
    SelfService selfService;

    @GetMapping("/my/{id}")
    public Result getBorrowerHome(@PathVariable Long id){

        return selfService.getBorrowerHome(id);
    }

    @PutMapping("{id}")
    public Result updateBorrower(@PathVariable Long id, @RequestBody User borrower){

        return selfService.updateBorrower(id,borrower);
    }

    @DeleteMapping("/{type}/{id}")
    public Result deleteBorrower(@PathVariable Long id,@PathVariable String type){

        return selfService.deleteBorrower(id,type);
    }



}
