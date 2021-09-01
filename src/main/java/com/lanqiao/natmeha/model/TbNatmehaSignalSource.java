package com.lanqiao.natmeha.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 号源维护记录表
 * @TableName tb_natmeha_signal_source
 */
@Data
public class TbNatmehaSignalSource implements Serializable {
    /**
     * 
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 医生code
     */
    private String doctorCode;

    /**
     * 挂号类别
     */
    private String registerType;

    /**
     * 就诊时间
     */
    private Date registerDate;

    /**
     * 预约量
     */
    private Integer registerCount;

    /**
     * 状态
     */
    private String status;

    /**
     * 
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

    /*
     *搜索栏内容搜索信息
     * */
    private String searchYear;

    private TbNatmehaDoctor tbNatmehaDoctor;

    private static final long serialVersionUID = 1L;
}