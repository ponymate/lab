package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.mapper.OutsiderMapper;
import com.mjw.lab.dao.mapper.StudentMapper;
import com.mjw.lab.dao.mapper.TeacherMapper;
import com.mjw.lab.dao.pojo.*;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.GetUserService;
import com.mjw.lab.utils.AdmThreadLocal;
import com.mjw.lab.utils.PermissionUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 16:48 2022/4/12
 * @Modified By:
 */
@Service
public class GetUserServiceImpl implements GetUserService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    OutsiderMapper outsiderMapper;

    @Autowired
    PermissionUtils permissionUtils;




    public Result getAllStudent(Map<String,String> pageParm) {
        Page<Student> studentPage = new Page<Student>(Integer.parseInt(pageParm.get("currentPage")), Integer.parseInt(pageParm.get("pageSize")));

        DeviceAdm deviceAdm = AdmThreadLocal.get();
        Borrower borrower = UserThreadLocal.get();

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();

        if(!(deviceAdm!=null||(borrower!=null&&borrower.getType().equals("teacher"))))
            return Result.fail(1005 ,"Insufficient permissions");

/*        wrapper.eq(Student::isExist,true);
        List<Student> students = studentMapper.selectList(wrapper);
        Page<Student> studentPage1 = studentMapper.selectPage(studentPage, wrapper);*/
        
        List<Student> students = null;
        if(borrower!=null&&borrower.getType().equals("teacher"))
            students = studentMapper.getTeacherStudent(studentPage,borrower.getId());
        else
            students = studentMapper.getAllStudent(studentPage);
        return Result.success(students);
    }


    public Result getAllOutsider(Map<String,String> page) {
        Page<Outsider> outsiderPage = new Page<Outsider>(Integer.parseInt(page.get("currentPage")),Integer.parseInt(page.get("pageSize")));

        if(permissionUtils.isNotAdm())
            return Result.fail(1005 ,"Insufficient permissions");

/*        LambdaQueryWrapper<Outsider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Outsider::isExist,true);
        List<Outsider> outsiders = outsiderMapper.selectList(wrapper);

        Page<Outsider> outsiderPage1 = outsiderMapper.selectPage(outsiderPage, wrapper);*/

        List<Outsider> outsiders = outsiderMapper.getAllOutsider(outsiderPage);
        return Result.success(outsiders);
    }


    public Result getAllTeacher(Map<String,String> page) {
        Page<Teacher> teacherPage = new Page<Teacher>(Integer.parseInt(page.get("currentPage")),Integer.parseInt(page.get("pageSize")));

        if(permissionUtils.isNotAdm())
            return Result.fail(1005 ,"Insufficient permissions");


/*        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::isExist,true);
        List<Teacher> teachers = teacherMapper.selectList(wrapper);

        Page<Teacher> teacherPage1 = teacherMapper.selectPage(teacherPage, wrapper);*/

        List<Teacher> teachers = teacherMapper.getAllTeacher(teacherPage);
        return Result.success(teachers);
    }

    @Override
    public Result getAllBorrower(String type, Map<String,String> page) {


        switch (type) {
            case "teacher":
                return getAllTeacher(page);
            case "student":
                return getAllStudent(page);
            case "outsider":
                return getAllOutsider(page);
            default:
                return null;
        }
    }

    @Override
    public Result count() {
        List<Map<String, Integer>> count = studentMapper.count();
        return Result.success(count);
    }


}
