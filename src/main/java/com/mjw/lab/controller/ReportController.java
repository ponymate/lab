package com.mjw.lab.controller;

import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReportFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:08 2022/6/27
 */

@CrossOrigin
@RestController
@RequestMapping("/reportForm")
public class ReportController {

    @Autowired
    private ReportFormService reportFormService;


    @GetMapping
    public Result getReportForm(@RequestParam(value="date") String date,@RequestParam(value="type") String type) throws ParseException {
        return reportFormService.getReportForm(date,type);
    }
}
