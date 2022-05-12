package com.mjw.lab.dao.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:27 2022/4/11
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFormVo {

    @TableId(type= IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long borrowerId;

    private Long teacherId;

    private Long deviceId;

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date beginTime;

    private Integer useTime;

    private Long deviceAdmId;


    private String type;

    //另外添加
    private String borrowerName;

    private String teacherState;

    private String admState;

    private String company;

    private String deviceType;

    private String major;

    private String department;

    private String tutorName;

    private boolean payed;

    private String superState;

    private String professionTitle;

    private String professionDirection;


}
