package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 权限菜单表
 * @TableName t_menu
 */
@Data
@ToString
public class Menu implements Serializable {
    /**
     * 主键，自增长
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单url（Controller 请求路径）
     */
    private String menuUrl;

    private static final long serialVersionUID = 1L;
}