package com.mjw.lab.service;

import com.mjw.lab.dao.vo.Result;

import java.text.ParseException;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:16 2022/6/27
 */
public interface ReportFormService {
    Result getReportForm(String date,String type) throws ParseException;
}
