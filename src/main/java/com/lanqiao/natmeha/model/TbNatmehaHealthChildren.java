package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 健康档案体检信息记录表
 * @TableName tb_natmeha_health_children
 */
@Data
@ToString
public class TbNatmehaHealthChildren{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * baseinfo中patient_id
     */
    private String patientId;

    /**
     * 新生儿姓名
     */
    private String newbornName;

    /**
     * 新生儿性别
     */
    private String newbornSex;

    /**
     * 新生儿出生日期
     */
    private Date newbornBirthDate;

    /**
     * 总检结论
     */
    private String examSummary;

    /**
     * 新生儿身份证件类别
     */
    private String newbornIdType;

    /**
     * 新生儿身份证件号码
     */
    private String newbornIdNo;

    /**
     * 父亲姓名
     */
    private String fatherName;

    /**
     * 母亲姓名
     */
    private String motherName;

    /**
     * 出生体重(g)
     */
    private BigDecimal birthWeight;

    /**
     * 出生身长
     */
    private BigDecimal birthLength;

    /**
     * 体重
     */
    private BigDecimal weight;

    /**
     * 身长
     */
    private BigDecimal length;

    /**
     * 喂养方式类别
     */
    private String feedWay;

    /**
     * 药物使用途径
     */
    private String drugRoute;

    /**
     * 每天吃奶次数
     */
    private Integer dailyFeedingTimes;

    /**
     * 每天吃奶量（mL)
     */
    private Integer dailyFeedingAmount;

    /**
     * 既往患病种类
     */
    private Integer stoolTimes;

    /**
     * 随访医生名称
     */
    private String visitDoctorName;

    /**
     * 本次访问日期
     */
    private Date thisVisitDate;

    /**
     * 下次访问日期
     */
    private Date nextVisitDate;

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