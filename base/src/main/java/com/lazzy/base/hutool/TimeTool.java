package com.lazzy.base.hutool;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class TimeTool {
    public static void main(String[] args) {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateUtil.parse(dateStr);
        //结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date, DateField.YEAR, -2);
        System.out.println(newDate);
    }
}
