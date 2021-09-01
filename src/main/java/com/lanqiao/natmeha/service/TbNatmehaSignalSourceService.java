package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaSignalSource;
import org.apache.ibatis.annotations.Param;

/**
*
*/
public interface TbNatmehaSignalSourceService {

    public Page<TbNatmehaSignalSource> selectAllNum(TbNatmehaSignalSource tbNatmehaSignalSource, int pageNum, int pageSize);

    public String getName(String doctorName);

//    public TbNatmehaSignalSource insertNum(TbNatmehaSignalSource tbNatmehaSignalSource);

    public TbNatmehaSignalSource selectByItemid(String itemid);

    public int updateNum(TbNatmehaSignalSource tbNatmehaSignalSource);

    public int deleteNum(int itemid);

    public int insertNum(TbNatmehaSignalSource tbNatmehaSignalSource);


}
