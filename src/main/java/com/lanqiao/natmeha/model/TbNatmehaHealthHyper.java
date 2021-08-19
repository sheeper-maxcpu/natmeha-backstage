package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 高血压患者随访信息记录表
 * @TableName tb_natmeha_health_hyper
 */
@Data
@ToString
public class TbNatmehaHealthHyper{
    /**
     * 
     */
    private Integer itemid;

    /**
     * baseinfo表中的patient_id
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
     * 心理调整评价结果
     */
    private String psyAdjustResult;

    /**
     * 随访遵医行为评价结果
     */
    private String complianceResult;

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