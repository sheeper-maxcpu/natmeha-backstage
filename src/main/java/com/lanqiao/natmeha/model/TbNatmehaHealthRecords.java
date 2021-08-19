package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 健康档案信息记录表
 * @TableName tb_natmeha_health_records
 */
@Data
@ToString
public class TbNatmehaHealthRecords{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 电话号码
     */
    private String phone;

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
     * 民族
     */
    private String nation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 文化程度
     */
    private String education;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * BMI
     */
    private String bmi;

    /**
     * 疾病史
     */
    private String diseaseHistory;

    /**
     * 过敏
     */
    private String allergy;

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

}