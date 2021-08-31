package com.lanqiao.natmeha.dao;

import com.lanqiao.natmeha.model.TbNatmehaFile;

public interface TbNatmehaFileDao {
    //int deleteByPrimaryKey(TbNatmehaFileKey key);

    /**
     * @param tbNatmehaFile
     * @return
     * 向文件表里面添加文件
     */
    int insert(TbNatmehaFile tbNatmehaFile);

    int insertSelective(TbNatmehaFile record);

    //TbNatmehaFile selectByPrimaryKey(TbNatmehaFileKey key);

    int updateByPrimaryKeySelective(TbNatmehaFile record);

    int updateByPrimaryKey(TbNatmehaFile record);
}