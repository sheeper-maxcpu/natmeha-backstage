package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 就诊费用记录表
 * @TableName tb_natmeha_med_cost
 */
@Data
@ToString
public class TbNatmehaMedCost{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 门诊记录表编号
     */
    private String outpatFormNo;

    /**
     * 就诊机构名称
     */
    private String visitOrgName;

    /**
     * 就诊科室名称
     */
    private String visitDeptName;

    /**
     * 就诊日期时间
     */
    private Date visitDtime;

    /**
     * 
     */
    private String specifications;

    /**
     * 
     */
    private Double price;

    /**
     * 
     */
    private String num;

    /**
     * 门诊个人承担费用金额（元）
     */
    private Double outpatSelfPayment;

    /**
     * 住院个人承担费用金额（元）
     */
    private Double inpatSelfPayment;

    /**
     * 
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
     * 
     */
    private Date itemupdateat;

}