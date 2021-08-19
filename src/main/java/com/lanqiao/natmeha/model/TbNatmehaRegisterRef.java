package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户挂号关系表
 * @TableName tb_natmeha_register_ref
 */
@Data
@ToString
public class TbNatmehaRegisterRef{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 预约号码
     */
    private Integer registerNum;

    /**
     * 号源code
     */
    private String sourceCode;

    /**
     * 用户code
     */
    private String userCode;

    /**
     * 就诊人code
     */
    private String patientCode;

    /**
     * 就诊状态
     */
    private String registerStatus;

    /**
     * 匿名评价
     */
    private Boolean anonymous;

    /**
     * 总体评分
     */
    private Double overallSource;

    /**
     * 诊疗效果评分
     */
    private Double effectSource;

    /**
     * 诊疗环境评分
     */
    private Double environmentSource;

    /**
     * 服务态度评分
     */
    private Double serviceSource;

    /**
     * 所患疾病
     */
    private String illness;

    /**
     * 就诊评分
     */
    private Double source;

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