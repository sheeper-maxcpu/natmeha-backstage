package com.lanqiao.natmeha.dao;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @Entity com.lanqiao.natmeha.model.TbNatmehaDoctor
*/

@Mapper
public interface TbNatmehaDoctorDao {
    public TbNatmehaDoctor selectDoctor(String itemCode);

    public Page<TbNatmehaDoctor> selectAllDoctor(@Param("tbNatmehaDoctor") TbNatmehaDoctor tbNatmehaDoctor,
                                                 @Param("pageNum") int pageNum,
                                                 @Param("pageSize")int pageSize);

    public String getFile(@Param("filePath") String filePath);

    public TbNatmehaDoctor insertDoctor(TbNatmehaDoctor tbNatmehaDoctor);

    public int deleteDoctor(int itemid);

    public TbNatmehaDoctor selectByItemid(Integer itemid);

    public int updateDoctor(TbNatmehaDoctor tbNatmehaDoctor);

    int deleteByPrimaryKey(String itemCode);

    int insert(TbNatmehaDoctor record);

    int insertSelective(TbNatmehaDoctor record);

    TbNatmehaDoctor selectByPrimaryKey(String itemCode);

    int updateByPrimaryKeySelective(TbNatmehaDoctor record);

    int updateByPrimaryKey(String itemCode);

    List<TbNatmehaDoctor> selectByName(String doctorName);

}
