package com.mjw.lab.dao.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String deviceType;

    @JSONField(format = "yyyy-MM-dd")
    private Date buyTime;

    private String manufacturer;

    private String purpose;

    private float price;

    private boolean exist = true;

}
