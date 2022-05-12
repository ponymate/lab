package com.mjw.lab.service;

import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.Borrower;
import com.mjw.lab.dao.pojo.Outsider;
import com.mjw.lab.dao.pojo.Student;
import com.mjw.lab.dao.pojo.Teacher;
import com.mjw.lab.dao.vo.Result;

public interface BorrowerService {

    Student getStudentByName(String name);

    Teacher getTeacherByName(String name);

    Outsider getOutsiderByName(String name);

    Student getStudentByIdAndPassword(String id, String password);

    Teacher getTeacherByIdAndPassword(String id, String password);

    Outsider getOutsiderByIdAndPassword(String id, String password);

    Borrower getBorrowerByIdAndPassword(String type, String id, String password);

    Borrower getBorrowerById(String type, Long id);

    Result deleteStudent(Long id);

    Result deleteOutsider(Long id);

    Result deleteTeacher(Long id);

    Result updateTeacher(User borrower);

    Result updateStudent(User borrower);

    Result updateOutsider(User borrower);

}
