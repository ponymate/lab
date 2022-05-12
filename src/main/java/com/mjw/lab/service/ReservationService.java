package com.mjw.lab.service;

import com.mjw.lab.dao.pojo.ReservationForm;
import com.mjw.lab.dao.vo.Result;

import java.util.Map;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 20:18 2022/4/7
 * @Modified By:
 */
public interface ReservationService {
    Result getReservation(Map<String, String> date);

    public void saveReservation(ReservationForm reservationForm);

    public void deleteReservation(Long id);
}
