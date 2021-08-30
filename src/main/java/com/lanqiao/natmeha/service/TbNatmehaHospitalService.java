package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHospital;

/**
 * @Author:YangZiYang
 * @create 2021/8/21 12:03
 */
public interface TbNatmehaHospitalService {
    public TbNatmehaHospital selectByKey(String itemCode);

    public Page<TbNatmehaHospital> selectForAll(TbNatmehaHospital hospital, int pageNum, int pageSize);

    public int insertNewHospital(TbNatmehaHospital tbNatmehaHospital);

}
