package com.xjsun.filestore.csv.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    // 映射CSV列"ID"到整型字段，自动类型转换
    // @CsvBindByName(column = "索引") 由于汉字有编码集问题可选按下标读取
    @CsvBindByPosition(position = 0)
    private int id;

    // required=true表示该字段不能为空（会触发验证）
    // @CsvBindByName(column = "用户名", required = true)
    @CsvBindByPosition(position = 1, required = true)
    private String name;

    // 可选字段，允许为空值
    // @CsvBindByName(column = "性别")
    @CsvBindByPosition(position = 2)
    private String gender;

    // 日期类型需要指定格式，value格式需与CSV中的日期字符串完全匹配
    // @CsvBindByName(column = "注册时间")
    @CsvBindByPosition(position = 3)
    @CsvDate(value = "yyyy/MM/dd")
    private Date registerDate;

    // 必须包含无参构造器（OpenCSV反射需要）
    public User() {}

    // 带参构造器
    public User(int id, String name, String gender, Date registerDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.registerDate = registerDate;
    }
}