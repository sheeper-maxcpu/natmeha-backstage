package com.lanqiao.natmeha.dao;

import generator.TbNatmehaChineseMedicine;
import generator.TbNatmehaChineseMedicineKey;

public interface TbNatmehaChineseMedicineDao {

    /**
     * @param key
     * @return
     * 根据主键删除信息
     */
    int deleteByPrimaryKey(TbNatmehaChineseMedicineKey key);

    int insert(TbNatmehaChineseMedicine record);

    int insertSelective(TbNatmehaChineseMedicine record);

    /**
     * @param tbNatmehaChineseMedicine
     * @return
     * 根据主键来查询所有中药名称信息
     */
    TbNatmehaChineseMedicine selectByPage(TbNatmehaChineseMedicine tbNatmehaChineseMedicine);

    int updateByPrimaryKeySelective(TbNatmehaChineseMedicine record);

    int updateByPrimaryKey(TbNatmehaChineseMedicine record);
}