package com.mjw.lab.service;

import com.mjw.lab.dao.parm.Login;
import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.Student;
import com.mjw.lab.dao.vo.Result;

import java.util.List;


public interface LoginService {

    public Result login(Login user);

    public Result registerBorrower(User borrower);

    Result saveStudentBatch(List<Student> students);
}
