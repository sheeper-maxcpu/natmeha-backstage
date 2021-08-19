package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 日志记录表
 * @TableName log
 */
@Data
@ToString
public class Log{
    /**
     * 
     */
    private Integer itemid;

    /**
     * uuid
     */
    private String itemcode;

    /**
     * 应用id
     */
    private String appCode;

    /**
     * 错误标题
     */
    private String logTitle;

    /**
     * 日志等级
     */
    private String logLevel;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 
     */
    private Date itemcreateat;

    /**
     * 
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date itemupdateat;

}