package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 国医堂信息记录表
 * @TableName tb_natmeha_hospital
 */
@Data
@ToString
public class TbNatmehaHospital{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * UUID
     */
    private String itemcode;

    /**
     * 国医堂名称
     */
    private String hospitalName;

    /**
     * 医院等级
     */
    private String hospitalLevel;

    /**
     * 预约量
     */
    private Integer hospitalBooking;

    /**
     * 评分
     */
    private Double hospitalSource;

    /**
     * 距离（暂定）
     */
    private Integer hospitalDistance;

    /**
     * 医院电话
     */
    private String hospitalPhone;

    /**
     * 医院地址省份
     */
    private String hospitalPro;

    /**
     * 医院地址市
     */
    private String hospitalCity;

    /**
     * 地址省份
     */
    private String hospitalCountry;

    /**
     * 医院详细地址
     */
    private String hospitalAdress;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 创建者
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
     * 简介
     */
    private byte[] introduce;

}