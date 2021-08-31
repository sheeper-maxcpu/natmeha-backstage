package com.lanqiao.natmeha.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_natmeha_hotspot
 * @author 
 */
@Data
public class TbNatmehaHotspot {

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

    /*
     *搜索栏内容搜索信息
     * */
    private String neirou;

    /*
     * 关联文件表
     * */
    private TbNatmehaFile tbNatmehaFile;

    private static final long serialVersionUID = 1L;
}