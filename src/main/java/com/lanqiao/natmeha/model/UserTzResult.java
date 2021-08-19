package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 
 * @TableName user_tz_result
 */
@Data
@ToString
public class UserTzResult{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 
     */
    private String itemcode;

    /**
     * 患者usercode
     */
    private String userCode;

    /**
     * 体质判定结果
     */
    private String tzDetermine;

    /**
     * 答题时间
     */
    private Date tcmRemark;

    /**
     * 体质判定结果:倾向是（基本是）
     */
    private String tzTendency;

}