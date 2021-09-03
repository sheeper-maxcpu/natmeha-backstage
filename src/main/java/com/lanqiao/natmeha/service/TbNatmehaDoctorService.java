package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
*/
public interface TbNatmehaDoctorService {
    public Page<TbNatmehaDoctor> selectAllDoctor(TbNatmehaDoctor tbNatmehaDoctor, int pageNum, int pageSize);

    public String getFile(String filePath);

//    public TbNatmehaDoctor insertDoctor(TbNatmehaDoctor tbNatmehaDoctor);

    public int deleteDoctor(int itemid);

    public int insertDoctor(TbNatmehaDoctor doctor);

    public TbNatmehaDoctor selectByItemid(Integer itemid);

    public int updateDoctor(TbNatmehaDoctor tbNatmehaDoctor);

    TbNatmehaDoctor getDoctorName(String itemCode);

    List<TbNatmehaDoctor> getDoctor(String doctorName);

}
