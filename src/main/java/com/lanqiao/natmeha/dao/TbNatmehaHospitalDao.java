package com.lanqiao.natmeha.dao;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbNatmehaHospitalDao {
    public TbNatmehaHospital selectByKey(String itemCode);

    public Page<TbNatmehaHospital> selectForAll(@Param("hospital") TbNatmehaHospital hospital, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    public int insertHospital(TbNatmehaHospital tbNatmehaHospital);

}