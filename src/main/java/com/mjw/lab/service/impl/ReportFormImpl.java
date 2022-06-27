package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mjw.lab.dao.mapper.DeviceMapper;
import com.mjw.lab.dao.mapper.ReservationFormMapper;
import com.mjw.lab.dao.pojo.Device;
import com.mjw.lab.dao.pojo.ReportForm;
import com.mjw.lab.dao.pojo.ReservationForm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReportFormService;
import com.mjw.lab.utils.PermissionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:16 2022/6/27
 */

@Service
public class ReportFormImpl implements ReportFormService {

    @Autowired
    private ReservationFormMapper reservationFormMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private PermissionUtils permissionUtils;

    @Override
    public Result getReportForm(String date,String type) throws ParseException {

        if(permissionUtils.isNotAdm())
            return Result.fail(1005,"权限不足");

        //Date
        DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
        Date newDate  = format.parse(date);
        List<ReservationForm> reservationForms = null;
        LambdaQueryWrapper<ReservationForm> wrapper = null;
        LambdaQueryWrapper<Device> dWrapper = null;
        switch (type) {
            case "day":

                wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ReservationForm::getBeginTime, newDate);

                reservationForms = reservationFormMapper.selectList(wrapper);

                break;
            case "week":
                wrapper = new LambdaQueryWrapper<>();
                wrapper.between(ReservationForm::getBeginTime, newDate, DateUtils.addDays(newDate, 7));

                reservationForms = reservationFormMapper.selectList(wrapper);
                break;
            case "month":
                wrapper = new LambdaQueryWrapper<>();
                wrapper.between(ReservationForm::getBeginTime, newDate, DateUtils.addMonths(newDate, 1));

                reservationForms = reservationFormMapper.selectList(wrapper);
                break;
            case "year":
                wrapper = new LambdaQueryWrapper<>();
                wrapper.between(ReservationForm::getBeginTime, newDate, DateUtils.addYears(newDate, 1));

                reservationForms =  reservationFormMapper.selectList(wrapper);
                break;
            default:
                return Result.fail(111, "format is wrong");
        }





        List<ReportForm> reportForm = new ArrayList<>();
        for (ReservationForm form :reservationForms) {

            Device device = deviceMapper.selectById(form.getDeviceId());
            if(device!=null)
                form.setType(device.getDeviceType());

            boolean flag = false;

            for (ReportForm report :reportForm) {
                if(report.getDeviceType().equals(form.getType())){
                    report.setTimes(report.getTimes()+1);
                    flag = true;
                    break;
                }

            }

            if(!flag)
                reportForm.add(new ReportForm(form.getType(),1));

        }

        return Result.success(reportForm);
    }
}
