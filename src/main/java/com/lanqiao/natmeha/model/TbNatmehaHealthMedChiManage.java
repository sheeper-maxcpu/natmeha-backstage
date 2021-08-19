package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 老年人中医药健康管理服务信息记录表
 * @TableName tb_natmeha_health_med_chi_manage
 */
@Data
@ToString
public class TbNatmehaHealthMedChiManage{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 姓名
     */
    private String patientId;

    /**
     * 责任医师姓名
     */
    private Integer identity1Score;

    /**
     * 随访方式
     */
    private Integer identity2Score;

    /**
     * 本次随访日期
     */
    private Integer identity3Score;

    /**
     * 摄盐量分级
     */
    private Integer identity4Score;

    /**
     * 目标摄盐量分级
     */
    private Integer identity5Score;

    /**
     * 心理调整评价结果
     */
    private Integer identity6Score;

    /**
     * 随访遵医行为评价结果
     */
    private Integer identity7Score;

    /**
     * 下次随访日期
     */
    private Integer identity8Score;

    /**
     * 随访医师姓名
     */
    private Integer identity9Score;

    /**
     * 主要体质
     */
    private String bodyType;

    /**
     * 情志调摄指导
     */
    private String guideFeeling;

    /**
     * 饮食调养指导
     */
    private String guideFood;

    /**
     * 起居调摄指导
     */
    private String guideLife;

    /**
     * 运动保健指导
     */
    private String guideSports;

    /**
     * 穴位保健指导
     */
    private String guidePoints;

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