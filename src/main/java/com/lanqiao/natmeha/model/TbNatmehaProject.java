package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 开展项目、功效特色记录表
 * @TableName tb_natmeha_project
 */
@Data
@ToString
public class TbNatmehaProject{
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * UUID
     */
    private String itemcode;

    /**
     * 开展项目、功效特色名称
     */
    private String name;

    /**
     * 项目、功效特色简介
     */
    private String content;

    /**
     * 数据区分
     */
    private String dataType;

    /**
     * 功效特色价格
     */
    private Integer price;

    /**
     * 数据状态
     */
    private String dataStatus;

    /**
     * 
     */
    private String userCode;

    /**
     * 浏览次数
     */
    private Integer visitNum;

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

    /**
     * 评分
     */
    private Double projectSorce;

}