package com.mjw.lab.controller;

import com.mjw.lab.dao.parm.Login;
import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.Student;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 借用人员的注册和所有人的登录
 */

@CrossOrigin
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody Login user){

        return loginService.login(user);
    }

    @PostMapping("/register")
    public Result register( @RequestBody User borrower){

        return loginService.registerBorrower(borrower);
    }

    @PostMapping("/borrower")
    public Result saveStudentBatch(@RequestBody List<Student> students){

        return loginService.saveStudentBatch(students);
    }


}
