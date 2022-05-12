package com.mjw.lab.service;

import com.mjw.lab.dao.parm.FormParm;
import com.mjw.lab.dao.pojo.ReservationForm;
import com.mjw.lab.dao.vo.ReservationFormVo;
import com.mjw.lab.dao.vo.Result;

import java.util.List;
import java.util.Map;

public interface ReservationFormService {
    Result creatForm(Long deviceId, FormParm reservationForm);


    Result getForm(Long id,String type);

    Result deleteForm(Long id);

    Result updateForm(Long id, FormParm reservationForm);

    Result getForms(Map<String, String> condition, String isChecked,Map<String,String> pageParm,String para);

    public List<ReservationFormVo> formToFormVo(List<ReservationForm> reservationForms);

    Result pay(Long id);
}
