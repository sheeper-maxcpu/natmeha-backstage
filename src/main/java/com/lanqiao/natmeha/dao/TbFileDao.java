package com.lanqiao.natmeha.dao;

import com.lanqiao.natmeha.model.TbNatmehaFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFileDao {

    int insert(TbNatmehaFile record);

    public TbNatmehaFile selectFile(String dateCode);

}