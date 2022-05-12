package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mjw.lab.dao.mapper.OutsiderMapper;
import com.mjw.lab.dao.mapper.StudentMapper;
import com.mjw.lab.dao.mapper.TeacherMapper;
import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.Borrower;
import com.mjw.lab.dao.pojo.Outsider;
import com.mjw.lab.dao.pojo.Student;
import com.mjw.lab.dao.pojo.Teacher;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.BorrowerService;
import com.mjw.lab.utils.PermissionUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    @Autowired
    public StudentMapper studentMapper;

    @Autowired
    public TeacherMapper teacherMapper;

    @Autowired
    public OutsiderMapper outsiderMapper;

    @Autowired
    PermissionUtils permissionUtils;

    @Override
    public Student getStudentByName(String name) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getName,name);
        return studentMapper.selectOne(wrapper);
    }

    @Override
    public Teacher getTeacherByName(String name) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getName,name);
        return teacherMapper.selectOne(wrapper);
    }

    @Override
    public Outsider getOutsiderByName(String name) {
        LambdaQueryWrapper<Outsider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Outsider::getName,name);
        return outsiderMapper.selectOne(wrapper);
    }


    @Override
    public Student getStudentByIdAndPassword(String id, String password) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId,id);
        wrapper.eq(Student::getPassword,password);
        return studentMapper.selectOne(wrapper);
    }

    @Override
    public Teacher getTeacherByIdAndPassword(String id, String password) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getId,id);
        wrapper.eq(Teacher::getPassword,password);
        return teacherMapper.selectOne(wrapper);
    }

    @Override
    public Outsider getOutsiderByIdAndPassword(String id, String password) {
        LambdaQueryWrapper<Outsider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Outsider::getId,id);
        wrapper.eq(Outsider::getPassword,password);
        return outsiderMapper.selectOne(wrapper);
    }

    @Override
    public Borrower getBorrowerByIdAndPassword(String type, String id, String password) {
        switch (type) {
            case "teacher":
                return getTeacherByIdAndPassword(id, password);
            case "student":
                return getStudentByIdAndPassword(id, password);
            case "outsider":
                return getOutsiderByIdAndPassword(id, password);
            default:
                return null;
        }
    }

    @Override
    public Borrower getBorrowerById(String type, Long id) {
        switch (type) {
            case "teacher":
                return teacherMapper.selectById(id);
            case "student":
                return studentMapper.selectById(id);
            case "outsider":
                return outsiderMapper.selectById(id);
            default:
                return null;
        }
    }

    public Result updateOutsider(User borrower) {
        Long id = UserThreadLocal.get().getId();
        Outsider o = outsiderMapper.selectById(id);
        String password = borrower.getPassword();
        if(password!=null)
            borrower.setPassword(DigestUtils.md5Hex(password));

        BeanUtils.copyProperties(borrower,o);
        LambdaQueryWrapper<Outsider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Outsider::getId,id);
        outsiderMapper.update(o,wrapper);

        o = outsiderMapper.selectById(id);
        return Result.success(o);
    }

    public Result updateStudent(User borrower) {
        Long id = UserThreadLocal.get().getId();
        Student s = studentMapper.selectById(id);
        String password = borrower.getPassword();
        if(password!=null)
            borrower.setPassword(DigestUtils.md5Hex(password));

        Teacher teacher = getTeacherByName(borrower.getTutorName());
        if(!(StringUtils.isBlank(borrower.getTutorName()))&&teacher ==null){
            return Result.fail(1004,"teacher is not exists");
        }

        BeanUtils.copyProperties(borrower,s);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId,id);
        studentMapper.update(s,wrapper);
        s = studentMapper.selectById(id);

        return Result.success(s);
    }

    public Result updateTeacher(User borrower) {
        Long id = UserThreadLocal.get().getId();
        Teacher t = teacherMapper.selectById(id);
        String password = borrower.getPassword();
        if(password!=null)
            borrower.setPassword(DigestUtils.md5Hex(password));


        BeanUtils.copyProperties(borrower,t);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getId,id);
        teacherMapper.update(t,wrapper);
        t = teacherMapper.selectById(id);

        return Result.success(t);
    }



    @Override
    public Result deleteStudent(Long id) {

        if(permissionUtils.isNotAdm()&&permissionUtils.isNotTeacher(id)&&permissionUtils.isNotSelf(id))
            return Result.fail(1005 ,"Insufficient permissions");

        Student student = studentMapper.selectById(id);
        student.setExist(false);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId,id);
        studentMapper.update(student,wrapper);

        return Result.success(student);
    }

    @Override
    public Result deleteOutsider(Long id) {

        if(permissionUtils.isNotSelf(id)&&permissionUtils.isNotAdm())
            return Result.fail(1005 ,"Insufficient permissions");

        Outsider outsider = outsiderMapper.selectById(id);
        outsider.setExist(false);
        LambdaQueryWrapper<Outsider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Outsider::getId,id);
        outsiderMapper.update(outsider,wrapper);

        return Result.success(outsider);
    }

    @Override
    public Result deleteTeacher(Long id) {

        if(permissionUtils.isNotSelf(id)&&permissionUtils.isNotAdm())
            return Result.fail(1005 ,"Insufficient permissions");


        Teacher teacher = teacherMapper.selectById(id);
        teacher.setExist(false);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getId,id);
        teacherMapper.update(teacher,wrapper);

        return Result.success(teacher);
    }



}
