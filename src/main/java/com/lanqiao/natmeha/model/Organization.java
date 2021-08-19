package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 机构表
 * @TableName organization
 */
@Data
@ToString
public class Organization{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 描述
     */
    private String orgDescription;

    /**
     * 标识符(用来标识机构类型)
     */
    private String orgIdentify;

    /**
     * 备用字段
     */
    private String backup1;

    /**
     * 备用字段
     */
    private String backup2;

    /**
     * 左值
     */
    private Integer left;

    /**
     * 右值
     */
    private Integer right;

    /**
     * 层级
     */
    private Integer level;

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