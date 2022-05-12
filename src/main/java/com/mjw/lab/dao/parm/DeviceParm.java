package com.mjw.lab.dao.parm;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceParm {

    private String deviceType;

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date buyTime;

    private String manufacturer;

    private String purpose;

    private float price;

    private int number = 1;

}
