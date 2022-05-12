package com.mjw.lab.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjw.lab.dao.pojo.ReservationForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ReservationFormMapper extends BaseMapper<ReservationForm> {

    public ArrayList<ReservationForm> getReservationOfStudent(Page<ReservationForm> page, Long teacherId);

    public ArrayList<ReservationForm> getReservationOfStudentIsCheck(Page<ReservationForm> page, Long teacherId);
}
