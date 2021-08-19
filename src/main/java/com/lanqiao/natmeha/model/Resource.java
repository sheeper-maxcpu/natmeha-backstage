package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 权限表
 * @TableName resource
 */
@Data
@ToString
public class Resource{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 权限名称
     */
    private String resourceName;

    /**
     * 权限类型
     */
    private String resourceType;

    /**
     * 父级id
     */
    private String resourcePcode;

    /**
     * 等级
     */
    private String resourceLevel;

    /**
     * 权限访问地址
     */
    private String resourceUrl;

    /**
     * 权限描述
     */
    private String resourceDescription;

    /**
     * 排序
     */
    private Integer sort;

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