package com.mjw.lab.service;

import com.mjw.lab.dao.vo.Result;

import java.util.Map;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 16:48 2022/4/12
 * @Modified By:
 */
public interface GetUserService {

    Result getAllBorrower(String type, Map<String,String> pageParm);

    Result count();
}
