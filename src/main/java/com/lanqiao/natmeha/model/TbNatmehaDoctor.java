package com.lanqiao.natmeha.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 医院服务团队记录表
 * @TableName tb_natmeha_doctor
 */
@Data
public class TbNatmehaDoctor implements Serializable {
    /**
     * 自增id
     */
    private Integer itemid;

    /**
     * UUID
     */
    private String itemcode;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 医生职称
     */
    private String doctorTitle;

    /**
     * 擅长治疗
     */
    private String doctorTreatment;

    /**
     * 评分
     */
    private Double doctorSorce;

    /**
     * 科室code（默认都为中医科）
     */
    private String deptCode;

    /**
     * 挂号类别
     */
    private String numType;

    /**
     * 用户code
     */
    private String userCode;

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
     * 机构（医院）itemcode
     */
    private String orgCode;

    /*
     *搜索栏内容搜索信息
     * */
    private String neirou;

    /*
     * 关联文件表
     * */
    private TbNatmehaFile tbNatmehaFile;

    private static final long serialVersionUID = 1L;

    public TbNatmehaFile setTbNatmehaFile(TbNatmehaFile tbNatmehaFile) {
        this.tbNatmehaFile = tbNatmehaFile;
        return this.tbNatmehaFile;
    }

    public Integer getItemid() {
        return itemid;
    }

}