package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表类型

 * @TableName dict
 */
@Data
@ToString
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 
     */
    private String dictIstree;

    /**
     * 描述
     */
    private String dictDescription;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
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