package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.mapper.*;
import com.mjw.lab.dao.parm.FormParm;
import com.mjw.lab.dao.pojo.*;
import com.mjw.lab.dao.vo.ReservationFormVo;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReservationFormService;
import com.mjw.lab.utils.AdmThreadLocal;
import com.mjw.lab.utils.PermissionUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationFormServiceImpl implements ReservationFormService {

    @Autowired
    ReservationFormMapper reservationFormMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    OutsiderMapper outsiderMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    PermissionUtils permissionUtils;


    @Override
    public Result creatForm(Long deviceId, FormParm reservationForm) {

        //检查数据是否全面并且正确，权限是否满足
        if(StringUtils.isAnyBlank(reservationForm.getUseTime().toString(),reservationForm.getBeginTime().toString())){
            return Result.fail(204,"All options cannot be empty");
        }

        if(UserThreadLocal.get()==null){
            return Result.fail(1005, "Insufficient permissions");
        }
        int time = reservationForm.getUseTime();
        if(time%2!=0){
            return Result.fail(1006,"data is wrong");
        }

        //插入预约单
        ReservationForm form = new ReservationForm();
        form.setDeviceId(deviceId);
        form.setBorrowerId(UserThreadLocal.get().getId());
        form.setType(UserThreadLocal.get().getType());
        BeanUtils.copyProperties(reservationForm,form);

        reservationFormMapper.insert(form);

        return Result.success(form);
    }


    @Override
    public Result getForms(Map<String, String> condition, String isChecked,Map<String,String> pageParm,String para) {

        List<ReservationForm> forms = null;
        Page<ReservationForm> page = new Page<>(Integer.parseInt(pageParm.get("currentPage")), Integer.parseInt(pageParm.get("pageSize")));

        switch (condition.get("type")) {
            case "student":
                forms = getFormOfStudentByTeacher(isChecked.equals("true"),page);
                break;
            case "all":
                forms = getAllForm(isChecked.equals("true"),page);
                break;
            case "my":
                forms = getMyForm(isChecked.equals("true"),page);
                break;
            case "outsider":
                forms = getOutsiderForm(isChecked.equals("true"),page);
        }

        List<ReservationFormVo> reservationFormVos = formToFormVo(forms);

        //根据para进行模糊查找
        if(para!=null){
            Iterator<ReservationFormVo> iterator = reservationFormVos.iterator();
            while (iterator.hasNext()) {
                ReservationFormVo form = iterator.next();
                if(!(form.getBorrowerName().contains(para))){
                    iterator.remove();
                }
            }
        }

        Collections.reverse(reservationFormVos);

        return Result.success(reservationFormVos);
    }




    @Override
    public List<ReservationFormVo> formToFormVo(List<ReservationForm> reservationForms){

        if(reservationForms==null){
            return null;
        }
        List<ReservationFormVo> formVos = new ArrayList<>();

        for (ReservationForm reservationForm :reservationForms) {
            ReservationFormVo formVo = new ReservationFormVo();
            BeanUtils.copyProperties(reservationForm,formVo);

            //添加借用人
            String type = reservationForm.getType();
            switch (type) {
                case "teacher":
                    Teacher teacher = teacherMapper.selectById(reservationForm.getBorrowerId());
                    formVo.setDepartment(teacher.getDepartment());
                    formVo.setBorrowerName(teacher.getName());
                    break;
                case "student":
                    Student student = studentMapper.selectById(reservationForm.getBorrowerId());
                    formVo.setDepartment(student.getDepartment());
                    formVo.setMajor(student.getMajor());
                    formVo.setBorrowerName(student.getName());
                    break;
                case "outsider":
                    Outsider outsider = outsiderMapper.selectById(reservationForm.getBorrowerId());
                    formVo.setCompany(outsider.getCompanyName());
                    formVo.setBorrowerName(outsider.getName());
                    break;
            }

            //添加老师姓名
            Teacher teacher = teacherMapper.selectById(reservationForm.getTeacherId());
            if(teacher!=null) {
                formVo.setTutorName(teacher.getName());
                formVo.setProfessionDirection(teacher.getProfessionDirection());
                formVo.setProfessionTitle(teacher.getProfessionTitle());
            }

            //添加设备
            Device device = deviceMapper.selectById(reservationForm.getDeviceId());
            if(device!=null)
                formVo.setDeviceType(device.getDeviceType());

            //添加老师是否签名
            if(reservationForm.getTeacherId()==null)
                formVo.setTeacherState("待审核");
            else if(reservationForm.getTeacherId()==0L)
                formVo.setTeacherState("未通过");
            else
                formVo.setTeacherState("已通过");

            //添加设备管理员是否签名
            if(reservationForm.getDeviceAdmId()==null)
                formVo.setAdmState("待审核");
            else if(reservationForm.getDeviceAdmId()==0L)
                formVo.setAdmState("未通过");
            else
                formVo.setAdmState("已通过");
            formVos.add(formVo);


        }

        return formVos;
    }

    @Override
    public Result pay(Long id) {
        if(permissionUtils.isNotOutsider())
            return Result.fail(1005, "Insufficient permissions");

        ReservationForm reservationForm = reservationFormMapper.selectById(id);
        if(!reservationForm.getSuperState().equals("已通过"))
            return Result.fail(1010, "请缴费");
        else
            reservationForm.setPayed(true);

        reservationFormMapper.updateById(reservationForm);

        ArrayList<ReservationForm> reservationForms = new ArrayList<>();
        reservationForms.add(reservationForm);
        ReservationFormVo reservationFormVo = formToFormVo(reservationForms).get(0);


        return Result.success(reservationFormVo);

    }


    public List<ReservationForm> getAllForm(boolean isChecked,Page<ReservationForm> page){

        if(permissionUtils.isNotAdm())
            return null;

        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        if(isChecked){
            wrapper.isNull(ReservationForm::getDeviceAdmId);
        }
        wrapper.eq(ReservationForm::isExist,true);




        Page<ReservationForm> reservationFormPage = reservationFormMapper.selectPage(page, wrapper);

        return reservationFormPage.getRecords();
    }



    public List<ReservationForm> getMyForm(boolean isChecked,Page<ReservationForm> page){


        Borrower borrower = UserThreadLocal.get();
        if(borrower==null){
            return null;
        }

        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getBorrowerId,borrower.getId());
        if(isChecked){
            wrapper.isNull(ReservationForm::getDeviceAdmId);
        }
        wrapper.eq(ReservationForm::isExist,true);



        Page<ReservationForm> reservationFormPage = reservationFormMapper.selectPage(page, wrapper);

        return reservationFormPage.getRecords();
    }

    private List<ReservationForm> getOutsiderForm(boolean isChecked, Page<ReservationForm> page) {

        if(permissionUtils.isNotAdm())
            return null;

        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        if(isChecked){
            wrapper.isNull(ReservationForm::getDeviceAdmId);
        }
        wrapper.eq(ReservationForm::isExist,true);
        wrapper.eq(ReservationForm::getType,"outsider");


        Page<ReservationForm> reservationFormPage = reservationFormMapper.selectPage(page, wrapper);

        return reservationFormPage.getRecords();

    }


    public List<ReservationForm> getFormOfStudentByTeacher(boolean isChecked,Page<ReservationForm> page){
        Borrower borrower = UserThreadLocal.get();
        if(borrower==null||!borrower.getType().equals("teacher")){
            return null;
        }

/*        //条件筛选
        Long teacherId = borrower.getId();
        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getType,"student");
        if(isChecked) {
            wrapper.isNull(ReservationForm::getDeviceAdmId);
        }
        wrapper.eq(ReservationForm::isExist,true);



        Page<ReservationForm> reservationFormPage = reservationFormMapper.selectPage(page, wrapper);

        List<ReservationForm> studentForm = reservationFormPage.getRecords();

        //将不是自己学生的去掉
        Iterator<ReservationForm> iterator = studentForm.iterator();
        while (iterator.hasNext()) {
            ReservationForm form = iterator.next();
            Student student = studentMapper.selectById(form.getBorrowerId());
            if(!(student.getTutorId().equals(teacherId))){
                iterator.remove();
            }
        }*/

        Long teacherId = borrower.getId();
        ArrayList<ReservationForm> reservationForm;
        if(isChecked) {
            reservationForm = reservationFormMapper.getReservationOfStudentIsCheck(page, teacherId);
        }
        else
            reservationForm = reservationFormMapper.getReservationOfStudent(page,teacherId);

        return reservationForm;
    }


    @Override
    public Result getForm(Long id,String type) {

        ReservationForm reservationForm = reservationFormMapper.selectById(id);


        //权限检查
        switch (type) {
            case "student":
                if(permissionUtils.isNotTeacher(reservationForm.getBorrowerId()))
                    return Result.fail(1005,"Insufficient permissions");
                break;
            case "all":
                if(permissionUtils.isNotAdm())
                    return Result.fail(1005,"Insufficient permissions");
                break;
            case "my":
                if(permissionUtils.isNotSelf(reservationForm.getBorrowerId()))
                    return Result.fail(1005,"Insufficient permissions");
                break;
            default:
                return Result.fail(500, "type is not right");
        }

        ArrayList<ReservationForm> reservationForms = new ArrayList<>();
        reservationForms.add(reservationForm);
        ReservationFormVo reservationFormVo = formToFormVo(reservationForms).get(0);


        return Result.success(reservationFormVo);
    }

    @Override
    public Result deleteForm(Long id) {

        ReservationForm form = reservationFormMapper.selectById(id);
        if(form==null){
            return Result.fail(1000,null);
        }
        Borrower borrower = UserThreadLocal.get();
        DeviceAdm deviceAdm = AdmThreadLocal.get();
        if(!borrower.getId().equals(form.getBorrowerId())&&deviceAdm==null){
            return Result.fail(1005,"Insufficient permissions");
        }

        form.setExist(false);
        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getId,id);
        reservationFormMapper.update(form,wrapper);

        return Result.success(form);
    }

    @Override
    public Result updateForm(Long id, FormParm reservationForm) {
        ReservationForm form = reservationFormMapper.selectById(id);

        Borrower borrower = UserThreadLocal.get();
        if(!borrower.getId().equals(form.getBorrowerId())){
            return Result.fail(1005,"Insufficient permissions");
        }

        if(StringUtils.isAllBlank(reservationForm.getUseTime().toString(),reservationForm.getBeginTime().toString())){
            return Result.fail(204,"All options cannot be empty");
        }
        if(!StringUtils.isBlank(reservationForm.getUseTime().toString())){
            form.setUseTime(reservationForm.getUseTime());
        }
        if(!StringUtils.isBlank(reservationForm.getBeginTime().toString())){
            form.setBeginTime(reservationForm.getBeginTime());
        }

        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getId,id);
        reservationFormMapper.update(form,wrapper);

        form = reservationFormMapper.selectById(id);

        return Result.success(form);
    }




}
