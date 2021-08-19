package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 2型糖尿病患者随访信息记录表
 * @TableName tb_natmeha_health_diabetes
 */
@Data
@ToString
public class TbNatmehaHealthDiabetes{
    /**
     * 
     */
    private Integer itemid;

    /**
     * baseinfo中的patient_id
     */
    private String patientId;

    /**
     * 责任医师姓名
     */
    private String respDoctorName;

    /**
     * 随访方式
     */
    private String visitWay;

    /**
     * 本次随访日期
     */
    private Date thisFollowupVisitDate;

    /**
     * 随访评价结果
     */
    private String visitEvalResult;

    /**
     * 下次随访日期
     */
    private Date nextFollowupDate;

    /**
     * 随访医师姓名
     */
    private String followupVisitDoctorName;

    /**
     * 症状名称
     */
    private String symptomName;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date itemcreateat;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date itemupdateat;

}