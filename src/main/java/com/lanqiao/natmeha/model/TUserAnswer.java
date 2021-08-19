package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 
 * @TableName t_user_answer
 */
@Data
@ToString
public class TUserAnswer {
    /**
     * 
     */
    private Integer itemid;

    /**
     * 
     */
    private String itemcode;

    /**
     * 患者id
     */
    private String userId;

    /**
     * 题目id
     */
    private String qId;

    /**
     * 患者答题选项
     */
    private String qOption;

    /**
     * 答题时间
     */
    private Date tcmRemark;

    /**
     * 体质类型id
     */
    private String typeId;

    /**
     * 患者本次答题题目序号
     */
    private String seq;

    /**
     * 是否生效（1：是  0：否）
     */
    private String useflag;

    /**
     * 预留字段，暂未使用
     */
    private String flag1;

    /**
     * 预留字段，暂未使用
     */
    private String flag2;

    /**
     * 预留字段，暂未使用
     */
    private String flag3;

    /**
     * 
     */
    private String uptflag;

    /**
     * 体质测评结果itemcode
     */
    private String resultItemcode;

}