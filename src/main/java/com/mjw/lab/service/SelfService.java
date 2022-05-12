package com.mjw.lab.service;

import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.vo.Result;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 16:47 2022/4/12
 * @Modified By:
 */
public interface SelfService {
    Result getBorrowerHome(Long id);

    Result updateBorrower(Long id, User borrower);

    Result deleteBorrower(Long id, String type);
}
