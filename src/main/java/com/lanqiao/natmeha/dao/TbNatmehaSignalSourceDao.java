package com.lanqiao.natmeha.dao;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaSignalSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @Entity com.lanqiao.natmeha.model.TbNatmehaSignalSource
*/
@Mapper
public interface TbNatmehaSignalSourceDao {

    public Page<TbNatmehaSignalSource> selectAllNum(@Param("tbNatmehaSignalSource") TbNatmehaSignalSource tbNatmehaSignalSource,
                                                 @Param("pageNum") int pageNum,
                                                 @Param("pageSize")int pageSize);

    public String getName(@Param("doctorName") String doctorName);

    public TbNatmehaSignalSource insertNum(TbNatmehaSignalSource tbNatmehaSignalSource);

    public int deleteNum(int itemid);

    public TbNatmehaSignalSource selectByItemid(String itemid);

    public int updateNum(TbNatmehaSignalSource tbNatmehaSignalSource);

    int insertSelective(TbNatmehaSignalSource record);

}
