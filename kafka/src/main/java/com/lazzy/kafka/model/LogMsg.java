package com.lazzy.kafka.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 封装日志信息实体类
 */
@Data
public class LogMsg {
    /** 日志入库时间 */
    private String logDate;
    /** 日志信息 */
    private String logMsg;

    public LogMsg(String logDate, String logMsg) {
        this.logDate = logDate;
        this.logMsg = logMsg;
    }
}
