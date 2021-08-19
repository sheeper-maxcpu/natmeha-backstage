package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 角色表
 * @TableName role
 */
@Data
@ToString
public class Role{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色说明
     */
    private String roleDescription;

    /**
     * 应用id
     */
    private String appCode;

    /**
     * 类型（0：普通，1：管理员）
     */
    private Integer roleType;

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