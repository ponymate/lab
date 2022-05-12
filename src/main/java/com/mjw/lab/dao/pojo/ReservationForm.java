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
public class ReservationForm {

    @TableId(type=IdType.AUTO)
    private Long id;

    private Long borrowerId;

    private Long teacherId;

    private Long deviceId;

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date beginTime;

    private Integer useTime;

    private Long deviceAdmId;

    private boolean payed=false;

    private String type;

    private boolean exist=true;

    private String superState="待审核";


}
