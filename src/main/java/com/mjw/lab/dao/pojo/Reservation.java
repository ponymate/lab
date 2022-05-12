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
public class Reservation {
    @TableId(type= IdType.AUTO)
    private Long formId;

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date beginTime;

    @JSONField(format = "yyyy-MM-dd hh:mm")
    private Date endTime;
}
