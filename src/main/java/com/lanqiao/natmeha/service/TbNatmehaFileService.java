package com.lanqiao.natmeha.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author barry
 * @create 2021-08-27 16:00
 */
public interface TbNatmehaFileService {
    int deleteByPrimaryKey(Long id);

    int insertFile(com.lanqiao.natmeha.model.TbNatmehaFile record);

    int insertSelective(com.lanqiao.natmeha.model.TbNatmehaFile record);

    com.lanqiao.natmeha.model.TbNatmehaFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(com.lanqiao.natmeha.model.TbNatmehaFile record);

    int updateByPrimaryKey(com.lanqiao.natmeha.model.TbNatmehaFile record);

    int updateFile(String itemcode,String fileName);
}
