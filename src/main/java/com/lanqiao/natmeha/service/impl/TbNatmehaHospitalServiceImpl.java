package com.lanqiao.natmeha.service.impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.dao.TbFileDao;
import com.lanqiao.natmeha.dao.TbNatmehaHospitalDao;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHospital;
import com.lanqiao.natmeha.service.TbNatmehaHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:YangZiYang
 * @create 2021/8/21 12:05
 */
@Service("hospitalService")
public class TbNatmehaHospitalServiceImpl implements TbNatmehaHospitalService {
    @Autowired
    TbNatmehaHospitalDao tbNatmehaHospitalDao;

    @Autowired
    TbFileDao tbFileDao;

    @Override
    public TbNatmehaHospital selectByKey(String itemCode) {
        return this.tbNatmehaHospitalDao.selectByKey(itemCode);
    }

    @Override
    public Page<TbNatmehaHospital> selectForAll(TbNatmehaHospital hospital, int pageNum, int pageSize) {
        return this.tbNatmehaHospitalDao.selectForAll(hospital, pageNum, pageSize);
    }

    @Override
    public int insertNewHospital(TbNatmehaHospital tbNatmehaHospital) {
        return this.tbNatmehaHospitalDao.insertHospital(tbNatmehaHospital);
    }




}
