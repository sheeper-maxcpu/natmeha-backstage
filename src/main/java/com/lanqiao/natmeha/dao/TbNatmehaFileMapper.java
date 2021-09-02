package com.lanqiao.natmeha.dao;

import com.lanqiao.natmeha.model.TbNatmehaFile;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.lanqiao.natmeha.model.TbNatmehaFile
 */
public interface TbNatmehaFileMapper {

    int deleteByPrimaryKey(Long id);

    int insertFile(TbNatmehaFile record);

    int insertSelective(TbNatmehaFile record);

    TbNatmehaFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaFile record);

    int updateByPrimaryKey(TbNatmehaFile record);

    int updateFile(@Param("itemcode") String itemcode,
                   @Param("fileName") String fileName);

}
