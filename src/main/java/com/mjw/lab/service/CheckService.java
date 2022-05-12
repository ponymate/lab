package com.mjw.lab.service;

import com.mjw.lab.dao.vo.Result;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 10:46 2022/4/7
 * @Modified By:
 */

public interface CheckService {
    Result teacherCheck(Long id,String pass);

    Result deviceAdmCheck(Long id,String pass);

    Result superAdmCheck(Long id, String pass);
}
