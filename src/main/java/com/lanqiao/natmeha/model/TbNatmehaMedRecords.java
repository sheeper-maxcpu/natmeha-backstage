package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 电子病历信息记录表
 * @TableName tb_natmeha_med_records
 */
@Data
@ToString
public class TbNatmehaMedRecords {
    /**
     * 
     */
    private Integer itemid;

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

    /**
     * 门诊记录表编号
     */
    private String outpatFormNo;

    /**
     * 身份证号
     */
    private String idcardNo;

    /**
     * 就诊机构名称
     */
    private String visitOrgName;

    /**
     * 就诊日期时间
     */
    private Date visitDtime;

    /**
     * 健康问题评估
     */
    private String healthProblemEval;

    /**
     * 就诊医师
     */
    private String respDoctorName;

    /**
     * 门诊诊断名称
     */
    private String outpatDiagName;

    /**
     * 诊断日期
     */
    private Date diagDate;

    /**
     * 入院科室名称
     */
    private String inDeptName;

    /**
     * 入院日期时间
     */
    private Date inDtime;

    /**
     * 出院日期时间
     */
    private Date outDtime;

    /**
     * 住院原因
     */
    private String inReasonCode;

    /**
     * 床号
     */
    private String inBed;

    /**
     * 出院科室名称
     */
    private String outDeptName;

    /**
     * 其他医学处置
     */
    private String otherMedicalTreatment;

    /**
     * 就诊科室名称
     */
    private String visitDeptName;

}