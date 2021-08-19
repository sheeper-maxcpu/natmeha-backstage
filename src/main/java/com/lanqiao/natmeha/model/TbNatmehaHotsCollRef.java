package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户和热点关系表（收藏）
 * @TableName tb_natmeha_hots_coll_ref
 */
@Data
@ToString
public class TbNatmehaHotsCollRef{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 热点code（中药常识code）
     */
    private String hotspotCode;

    /**
     * 用户code
     */
    private String userCode;

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