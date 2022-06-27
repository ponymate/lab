package com.mjw.lab.controller;

import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.GetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/borrower")
public class GetUserController {

    @Autowired
    private GetUserService getUserService;

    @GetMapping("/{type}")
    public Result getAllBorrower(@PathVariable String type, @RequestParam Map<String,String> pageParm ,@RequestParam(required = false,value = "name")String name){
        return getUserService.getAllBorrower(type,pageParm,name);
    }



}
