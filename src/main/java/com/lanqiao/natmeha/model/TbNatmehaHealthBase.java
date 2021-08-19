package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 健康档案基本信息记录表
 * @TableName tb_natmeha_health_base
 */
@Data
@ToString
public class TbNatmehaHealthBase {
    /**
     * 
     */
    private Integer itemid;

    /**
     * 
     */
    private String patientId;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 证件类型代码
     */
    private String idType;

    /**
     * 身份证件号码
     */
    private String idNo;

    /**
     * 性别(0-未知的性别、1-男性、2-女性、9-未说明的性别)
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 文化程度
     */
    private String education;

    /**
     * 户口性质
     */
    private String accoProperty;

    /**
     * 婚姻状况
     */
    private String marriage;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 出生详细地址
     */
    private String birthPlace;

    /**
     * 既往患病种类
     */
    private String pastSicknessType;

    /**
     * 家族性疾病名称
     */
    private String familyDisease;

    /**
     * 患者与本人关系
     */
    private String patientRelation;

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