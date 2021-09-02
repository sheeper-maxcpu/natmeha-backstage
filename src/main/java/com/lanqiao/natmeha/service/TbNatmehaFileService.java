package com.lanqiao.natmeha.service;

import com.lanqiao.natmeha.model.TbNatmehaFile;
import org.apache.ibatis.annotations.Param;

/**
 * @author barry
 * @create 2021-08-27 16:00
 */
public interface TbNatmehaFileService {
    int deleteByPrimaryKey(Long id);

    int insertFile(TbNatmehaFile record);

    int insertSelective(TbNatmehaFile record);

    TbNatmehaFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaFile record);

    int updateByPrimaryKey(TbNatmehaFile record);

    int updateFile(String itemcode,String fileName);
}
