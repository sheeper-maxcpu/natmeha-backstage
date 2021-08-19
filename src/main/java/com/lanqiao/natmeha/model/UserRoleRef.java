package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户--角色  关联表
 * @TableName user_role_ref
 */
@Data
@ToString
public class UserRoleRef {
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 关联user表itemCode字段
     */
    private String userCode;

    /**
     * 应用系统code
     */
    private String appCode;

    /**
     * 关联role表itemCode字段（应用系统角色）
     */
    private String roleCode;

    /**
     * 平台角色
     */
    private String platRole;

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