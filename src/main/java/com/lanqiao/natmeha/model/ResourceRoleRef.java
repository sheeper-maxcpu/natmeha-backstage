package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 权限--角色  关联表
 * @TableName resource_role_ref
 */
@Data
@ToString
public class ResourceRoleRef{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 
     */
    private String resourceCode;

    /**
     * 
     */
    private String roleCode;

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