package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

/**
 * 体质类型

 * @TableName t_knowledge_tcm_type
 */
@Data
@ToString
public class TKnowledgeTcmType {
    /**
     * 
     */
    private String typeId;

    /**
     * 体质名称
     */
    private String typeName;

    /**
     * 体质类型
     */
    private String typeNumber;

    /**
     * 体质特征
     */
    private String lbZttz;

    /**
     * 形体特征
     */
    private String lbXttz;

    /**
     * 常见表现
     */
    private String lbCjbx;

    /**
     * 心理特征
     */
    private String lbXltz;

    /**
     * 发病倾向
     */
    private String lbFbqx;

    /**
     * 对外界环境适应能力
     */
    private String lbDwjhjsynl;

    /**
     * 测评结果
     */
    private String tlZttz;

    /**
     * 针对人群
     */
    private String tlZdrq;

    /**
     * 情志调摄
     */
    private String tlQztj;

    /**
     * 饮食调养
     */
    private String tlTyfsYs;

    /**
     * 起居调摄
     */
    private String tlTyfsQj;

    /**
     * 运动保健
     */
    private String tlTyfsYd;

    /**
     * 穴位保健
     */
    private String tlTyfsXwbj;

    /**
     * 
     */
    private String optionsText;

    /**
     * 
     */
    private String optionsValue;

    /**
     * 
     */
    private String delFlag;

    /**
     * 
     */
    private String uptflag;

}