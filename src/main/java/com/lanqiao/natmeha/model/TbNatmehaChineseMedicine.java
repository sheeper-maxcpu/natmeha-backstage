package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 中药常识信息记录表
 * @TableName tb_natmeha_chinese_medicine
 */
@Data
@ToString
public class TbNatmehaChineseMedicine{
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 中药材名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 功效分类
     */
    private String classification;

    /**
     * 采制
     */
    private String harvesting;

    /**
     * 性味
     */
    private String taste;

    /**
     * 归经
     */
    private String merTropism;

    /**
     * 主治
     */
    private String governance;

    /**
     * 用法用量
     */
    private String usage;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 用户code
     */
    private String userCode;

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