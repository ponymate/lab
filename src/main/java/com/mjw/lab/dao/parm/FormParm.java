package com.mjw.lab.dao.parm;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FormParm {

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date beginTime;

    private Integer useTime;
}
