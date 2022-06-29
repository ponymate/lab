package com.mjw.lab.service.impl;

import com.mjw.lab.dao.mapper.OutsiderMapper;
import com.mjw.lab.dao.mapper.StudentMapper;
import com.mjw.lab.dao.mapper.TeacherMapper;
import com.mjw.lab.dao.parm.Login;
import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.*;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.BorrowerService;
import com.mjw.lab.service.EmpService;
import com.mjw.lab.service.LoginService;
import com.mjw.lab.utils.JwtUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private OutsiderMapper outsiderMapper;

    @Autowired
    private EmpService empService;

    /**
     * 学生注册并检测老师是否存在，自己输入id
     *
     */
    public Result registerStudent(User student) {
        if(StringUtils.isBlank(student.getId().toString())){
            return Result.fail(204,"All options cannot be empty2");
        }

        Teacher teacher = borrowerService.getTeacherByName(student.getTutorName());
        if(teacher ==null){
            return Result.fail(1004,"teacher is not exists");
        }
        if(studentMapper.selectById(student.getId())!=null){
            return Result.fail(1002,"Id already exists");
        }
        student.setTutorId(teacher.getId());
        student.setPassword(DigestUtils.md5Hex(student.getPassword()));
        Student s = new Student();
        BeanUtils.copyProperties(student,s);

        studentMapper.insert(s);

        String token = JwtUtils.getJwtToken(student.getId(),student.getName(),"student");

        return Result.success(token);
    }

    /**
     * 老师注册，自己输入id
     * @param teacher
     * @return
     */
    public Result registerTeacher(User teacher) {
        if(StringUtils.isAnyBlank(teacher.getId().toString())){
            return Result.fail(204,"All options cannot be empty");
        }
        if(teacherMapper.selectById(teacher.getId())!=null){
            return Result.fail(1002,"Id already exists");
        }

        teacher.setPassword(DigestUtils.md5Hex(teacher.getPassword()));
        Teacher t = new Teacher();
        BeanUtils.copyProperties(teacher,t);

        teacherMapper.insert(t);

        String token = JwtUtils.getJwtToken(teacher.getId(),teacher.getName(),"teacher");

        return Result.success(token);
    }

    /**
     * 不需输入id
     * @param outsider
     * @return
     */
    public Result registerOutsider(User outsider) {

        outsider.setPassword(DigestUtils.md5Hex(outsider.getPassword()));
        Outsider o = new Outsider();
        BeanUtils.copyProperties(outsider,o);

        outsiderMapper.insert(o);

        String token = JwtUtils.getJwtToken(o.getId(),o.getName(),o.getType());

        return Result.success(o.getId().toString());
    }

    @Override
    public Result registerBorrower(User borrower) {
        if(StringUtils.isAnyBlank(borrower.getName(),borrower.getPassword(),borrower.getType())){
            return Result.fail(204,"All options cannot be empty1");
        }

        switch (borrower.getType()) {
            case "teacher":
                return registerTeacher(borrower);
            case "student":
                return registerStudent(borrower);
            case "outsider":
                return registerOutsider(borrower);
            default:
                return Result.fail(500, "error");
        }
    }


    @Override
    public Result login(Login user) {
        if(StringUtils.isAnyBlank(user.getPassword(),user.getId().toString(),user.getType())){
            return Result.fail(204,"All options cannot be empty");
        }
        String password = DigestUtils.md5Hex(user.getPassword());

        DeviceAdm deviceAdm = null;
        Borrower borrower = null;
        if(user.getType().equals("deviceAdm")||user.getType().equals("superAdmin"))
            deviceAdm = empService.getDeviceAdmByIdAndPassword(user.getId().toString(),password);
        else
            borrower = borrowerService.getBorrowerByIdAndPassword(user.getType(),user.getId().toString(),password);

        String token = null;
        if(borrower!=null)
            token = JwtUtils.getJwtToken(borrower.getId(), borrower.getName(),user.getType());
        else if(deviceAdm!=null)
            token = JwtUtils.getJwtToken(deviceAdm.getId(), deviceAdm.getName(),user.getType());
        else
            return Result.fail(1000,"username or password is not right");


        return Result.success(token);
    }

    @Override
    public Result saveStudentBatch(List<Student> students) {
        //身份验证
        Borrower borrower = UserThreadLocal.get();
        Long id = borrower.getId();
        if(!borrower.getType().equals("teacher")){
            return Result.fail(1005,"Insufficient user rights");
        }


        for (Student student :students) {
            if(StringUtils.isAnyBlank(student.getId().toString(),student.getName())){
                return Result.fail(204,"All options cannot be empty");
            }
            if(studentMapper.selectById(student.getId())!=null){
                return Result.fail(1002,"Id already exists");
            }

            student.setTutorId(id);
            student.setType("student");
            student.setPassword(DigestUtils.md5Hex(student.getId().toString()));
            studentMapper.insert(student);
        }
        return Result.success(students);
    }



}
