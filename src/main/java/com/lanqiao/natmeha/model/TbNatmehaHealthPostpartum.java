package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 产后42天健康健康检查记录表
 * @TableName tb_natmeha_health_postpartum
 */
@Data
@ToString
public class TbNatmehaHealthPostpartum{
    /**
     * 
     */
    private Integer itemid;

    /**
     * baseinfo中patient_id
     */
    private String patientId;

    /**
     * 健康状况详细描述
     */
    private String healthConditionDescr;

    /**
     * 心理状况详细描述
     */
    private String psychologyConditionDescr;

    /**
     * 伤口愈合状况
     */
    private String woundHealing;

    /**
     * 产妇健康状况评估描述
     */
    private String puerperaHealthEvalDescr;

    /**
     * 访视医师姓名
     */
    private String visitDoctorName;

    /**
     * 孕产妇健康指导类别
     */
    private String pregHealthGuide;

    /**
     * 备注
     */
    private String notes;

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