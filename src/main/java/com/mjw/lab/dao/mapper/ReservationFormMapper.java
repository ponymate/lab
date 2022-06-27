package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.ReservationForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationFormMapper extends BaseMapper<ReservationForm> {


    public Page<ReservationForm> getReservationOfAll(Page<ReservationForm> page);

    public Page<ReservationForm> getReservationOfAllByName(Page<ReservationForm> page,String name);

    public Page<ReservationForm> getReservationOfAllIsCheck(Page<ReservationForm> page);

    public Page<ReservationForm> getReservationOfAllIsCheckByName(Page<ReservationForm> page,String name);

    public Page<ReservationForm> getReservationOfMyIsCheckByName(Page<ReservationForm> page, String name ,Long id);

    public Page<ReservationForm> getReservationOfMyIsCheck(Page<ReservationForm> page,Long id);

    public Page<ReservationForm> getReservationOfMyByName(Page<ReservationForm> page, String name,Long id);

    public Page<ReservationForm> getReservationOfMy(Page<ReservationForm> page,Long id);

    public Page<ReservationForm> getReservationOfOutIsCheckByName(Page<ReservationForm> page, String name);

    public Page<ReservationForm> getReservationOfOutIsCheck(Page<ReservationForm> page);

    public Page<ReservationForm> getReservationOfOutByName(Page<ReservationForm> page, String name);

    public Page<ReservationForm> getReservationOfOut(Page<ReservationForm> page);

    public Page<ReservationForm> getReservationOfStuIsCheckByName(Page<ReservationForm> page, String name,Long teacher);

    public Page<ReservationForm> getReservationOfStuByName(Page<ReservationForm> page, String name,Long teacher);

    public Page<ReservationForm> getReservationOfStuIsCheck(Page<ReservationForm> page,Long teacher);

    public Page<ReservationForm> getReservationOfStu(Page<ReservationForm> page,Long teacher);
}
