package com.mjw.lab.controller;

import com.mjw.lab.dao.parm.FormParm;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.ReservationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 预约申请单的维护，增删改查
 */

@CrossOrigin
@RestController
@RequestMapping
public class ReservationFormController {

    @Autowired
    private ReservationFormService reservationFormService;

    @PostMapping("/reservationForm/{id}")
    public Result creatForm(@PathVariable("id")Long deviceId, @RequestBody FormParm reservationForm){
        return reservationFormService.creatForm(deviceId,reservationForm);
    }

    @GetMapping("/reservationForm")
    public Result getForms(@RequestParam Map<String,String> condition,
                           @RequestParam(value = "isChecked",defaultValue = "false") String isChecked,
                            @RequestParam Map<String,String> pageParm,
                            @RequestParam(required = false,value = "like") String para){

        return reservationFormService.getForms(condition,isChecked,pageParm,para);

    }

    @GetMapping("/reservationForm/{type}/{id}")
    public Result getForm(@PathVariable Long id,@PathVariable String type){
        return reservationFormService.getForm(id,type);
    }


    @DeleteMapping("/reservationForm/{id}")
    public Result deleteForm(@PathVariable Long id){
        return reservationFormService.deleteForm(id);
    }

    @PutMapping("/reservationForm/{id}")
    public Result putForm(@PathVariable Long id , @RequestBody FormParm reservationForm){

        return reservationFormService.updateForm(id,reservationForm);
    }

    @PutMapping("/reservationForm/{id}/pay")
    public Result pay(@PathVariable Long id){

        return reservationFormService.pay(id);
    }

}
