package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 今日热点记录表
 * @TableName tb_natmeha_hotspot
 */
@Data
@ToString
public class TbNatmehaHotspot{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * UUID
     */
    private String itemcode;

    /**
     * 热点名称
     */
    private String hotspotTitle;

    /**
     * 介绍
     */
    private String hotspotContent;

    /**
     * 数据状态
     */
    private String dataStatus;

    /**
     * 数据类型(用来区分处中药常识以外几项）
     */
    private String dataType;

    /**
     * 
     */
    private String userCode;

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

}