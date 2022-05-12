package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mjw.lab.dao.mapper.ReservationFormMapper;
import com.mjw.lab.dao.mapper.StudentMapper;
import com.mjw.lab.dao.pojo.ReservationForm;
import com.mjw.lab.dao.vo.ReservationFormVo;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.CheckService;
import com.mjw.lab.service.ReservationFormService;
import com.mjw.lab.service.ReservationService;
import com.mjw.lab.utils.AdmThreadLocal;
import com.mjw.lab.utils.PermissionUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 10:46 2022/4/7
 * @Modified By:
 */

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private ReservationFormMapper reservationFormMapper;

    @Autowired
    private ReservationFormService reservationFormService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    PermissionUtils permissionUtils;


    @Override
    public Result teacherCheck(Long id,String pass) {

        //权限检查
        ReservationForm reservationForm = reservationFormMapper.selectById(id);
        if(!studentMapper.selectById(reservationFormMapper.selectById(id).getBorrowerId()).getTutorId().equals(UserThreadLocal.get().getId()))
            return Result.fail(1005,"权限不足");

        //修改数据库（签名）
        if(pass.equals("no")){
            reservationForm.setTeacherId(0L);
        }else if(pass.equals("yes")){
            reservationForm.setTeacherId(UserThreadLocal.get().getId());
        }
        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getId,id);
        reservationFormMapper.update(reservationForm,wrapper);

        //返回数据（数据修改）
        List<ReservationForm> forms = new ArrayList<>();
        forms.add(reservationForm);
        List<ReservationFormVo> reservationFormVos = reservationFormService.formToFormVo(forms);
        ReservationFormVo reservationFormVo = reservationFormVos.get(0);

        return Result.success(reservationFormVo);
    }


    public boolean checkTime(List<ReservationForm> forms, ReservationForm theForm){

        Date theBeginTime = theForm.getBeginTime();
        Date theEndTime = DateUtils.addHours(theForm.getBeginTime(), theForm.getUseTime());
        Long theDeviceId = theForm.getDeviceId();

        for (ReservationForm form :forms) {
            Long deviceId = form.getDeviceId();
            if(deviceId.equals(theDeviceId)){
                Date beginTime = form.getBeginTime();
                Date endTime = DateUtils.addHours(form.getBeginTime(), form.getUseTime());

                if((beginTime.after(theBeginTime)&&endTime.before(theEndTime))||
                        (beginTime.after(theBeginTime)&&beginTime.before(theEndTime))||
                            (endTime.after(theBeginTime)&& endTime.before(theEndTime))){

                    return false;
                }

            }
        }

        return true;
    }


    @Override
    public Result deviceAdmCheck(Long id,String pass) {
        //权限检查
        ReservationForm reservationForm = reservationFormMapper.selectById(id);
        if(AdmThreadLocal.get()==null){
            return Result.fail(1005,"权限不足");
        }

        //修改数据库（签名）
        if(pass.equals("no")){
            reservationForm.setDeviceAdmId(0L);
            reservationService.deleteReservation(id);
        }else if(pass.equals("yes")){
            reservationForm.setDeviceAdmId(AdmThreadLocal.get().getId());
            reservationService.saveReservation(reservationForm);
        }
        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getId,id);
        reservationFormMapper.update(reservationForm,wrapper);


        //返回数据（数据修改）
        List<ReservationForm> forms = new ArrayList<>();
        forms.add(reservationForm);
        List<ReservationFormVo> reservationFormVos = reservationFormService.formToFormVo(forms);
        ReservationFormVo reservationFormVo = reservationFormVos.get(0);



        return Result.success(reservationFormVo);

    }

    @Override
    public Result superAdmCheck(Long id, String pass) {
        //权限检查
        ReservationForm reservationForm = reservationFormMapper.selectById(id);
        if(permissionUtils.isNotBoss())
            return Result.fail(1005,"权限不足");

        //修改数据库（签名）
        if(pass.equals("no")){
            reservationForm.setSuperState("未通过");
        }else if(pass.equals("yes")){
            reservationForm.setSuperState("已通过");
        }
        LambdaQueryWrapper<ReservationForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReservationForm::getId,id);
        reservationFormMapper.update(reservationForm,wrapper);


        //返回数据（数据修改）
        List<ReservationForm> forms = new ArrayList<>();
        forms.add(reservationForm);
        List<ReservationFormVo> reservationFormVos = reservationFormService.formToFormVo(forms);
        ReservationFormVo reservationFormVo = reservationFormVos.get(0);



        return Result.success(reservationFormVo);
    }


}
