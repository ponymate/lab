package com.mjw.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mjw.lab.dao.mapper.ReservationMapper;
import com.mjw.lab.dao.pojo.Reservation;
import com.mjw.lab.dao.pojo.ReservationForm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReservationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 20:19 2022/4/7
 * @Modified By:
 */

@Service
public class ReservationServiceImpl implements ReservationService {


    @Autowired
    ReservationMapper reservationMapper;

    public List<Reservation> getYearReservation(String year,List<Reservation> reservations){

        List<Reservation> yearReservation = new ArrayList<>();

        for (Reservation reservation :reservations) {
            Calendar c = Calendar.getInstance();
            c.setTime(reservation.getBeginTime());
            Integer y = c.get(Calendar.YEAR);
            if(y.toString().equals(year)){
                yearReservation.add(reservation);
            }
        }

        return yearReservation;
    }

    public  List<Reservation> getMonth(String month, List<Reservation> reservations) {
        List<Reservation> monthReservation = new ArrayList<>();

        for (Reservation reservation :reservations) {
            Calendar c = Calendar.getInstance();
            c.setTime(reservation.getBeginTime());
            Integer m = c.get(Calendar.MONTH);
            if(m.toString().equals(month)){
                monthReservation.add(reservation);
            }
        }

        return monthReservation;
    }

    public List<Reservation> getWeek(List<Reservation> reservations){
        List<Reservation> weekReservation = new ArrayList<>();

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

        for (Reservation reservation :reservations) {
            Date beginTime = DateUtils.addDays(reservation.getBeginTime(), 7);
            if(beginTime.after(date)){
                weekReservation.add(reservation);
            }
        }

        return weekReservation;
    }

    @Override
    public Result getReservation(Map<String, String> date) {
        String year = date.get("year");
        String month = date.get("month");
        String week = date.get("week");
        List<Reservation> reservations = reservationMapper.selectList(null);

        if(year!=null){
            reservations = getYearReservation(year,reservations);
            if(month!=null){
                reservations = getMonth(month,reservations);

            }
        }
        if(week!=null){
            reservations = getWeek(reservations);
        }

        return Result.success(reservations);
    }

    @Override
    public void saveReservation(ReservationForm reservationForm) {
        Reservation reservation = new Reservation();

        reservation.setBeginTime(reservationForm.getBeginTime());

        reservation.setFormId(reservationForm.getId());

        reservation.setEndTime(DateUtils.addHours(reservationForm.getBeginTime(),reservationForm.getUseTime()));

        reservationMapper.insert(reservation);
    }

    @Override
    public void deleteReservation(Long id) {


        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Reservation::getFormId,id);
        reservationMapper.delete(wrapper);

    }


}
