package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 就诊人信息记录表
 * @TableName tb_natmeha_patient
 */
@Data
@ToString
public class TbNatmehaPatient{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 就诊人名称
     */
    private String patientName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证件类型
     */
    private String idcardType;

    /**
     * 证件号码
     */
    private String idcardNo;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 关系
     */
    private String relationship;

    /**
     * 是否为默认就诊人
     */
    private String defaultPatient;

    /**
     * 地址省份code
     */
    private String adressPro;

    /**
     * 市级code
     */
    private String adressCity;

    /**
     * 地区省份
     */
    private String adressCountry;

    /**
     * 地址(手动输入)
     */
    private String adress;

    /**
     * 婚姻状况
     */
    private String maritalStatus;

    /**
     * 用户code
     */
    private String userCode;

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

    /**
     * 
     */
    private Date birthdate;

}