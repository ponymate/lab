package com.mjw.lab.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:12 2022/6/27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportForm {

    private String deviceType;

    private int times;
}
