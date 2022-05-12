package com.mjw.lab.controller;

import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 20:14 2022/4/7
 * @Modified By:
 */

@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping
    public Result getReservation(@RequestParam(required = false) Map<String,String> date){
        return reservationService.getReservation(date);
    }

}
