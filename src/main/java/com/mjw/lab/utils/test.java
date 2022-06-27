package com.mjw.lab.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 9:35 2022/6/27
 */
public class test {

    public static void main(String[] args) throws ParseException {
        String date1 = "2002-04-06";

        String date2 = "Sun Jun 19 2022 00:00:00 GMT+0800 (中国标准时间)";

        DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
        Date date  = format.parse(date2);
        System.out.println(date);
        Date datea= DateUtils.parseDate(date1,"yyyy-MM-dd","F MM dd yyyy");
        //Date dateb = DateUtils.parseDate(date2, "EEE MMM dd yyyy");

        System.out.println(datea);
        //System.out.println(dateb);

    }
}
