package com.lanqiao.natmeha.model;

import lombok.Data;
import lombok.ToString;

/**
 * 问卷题库表
 * @TableName t_knowledge_tcm_question
 */
@Data
@ToString
public class TKnowledgeTcmQuestion{
    /**
     * 
     */
    private String qId;

    /**
     * 
     */
    private String typeId;

    /**
     * 
     */
    private String qNumber;

    /**
     * 
     */
    private String qText;

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
    private String feaId;

    /**
     * 
     */
    private String qSex;

}