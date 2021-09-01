package com.lanqiao.natmeha.service.impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.dao.TbNatmehaSignalSourceDao;
import com.lanqiao.natmeha.model.TbNatmehaSignalSource;
import com.lanqiao.natmeha.service.TbNatmehaSignalSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
*
*/
@Service("tbNatmehaSignalSourceService")
@Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
public class TbNatmehaSignalSourceServiceImpl implements TbNatmehaSignalSourceService{

    @Autowired
    TbNatmehaSignalSourceDao tbNatmehaSignalSourceDao;


    @Override
    public Page<TbNatmehaSignalSource> selectAllNum(TbNatmehaSignalSource tbNatmehaSignalSource, int pageNum, int pageSize) {
        return this.tbNatmehaSignalSourceDao.selectAllNum(tbNatmehaSignalSource,pageNum,pageSize);
    }

    @Override
    public String getName(String doctorName) {
        return this.tbNatmehaSignalSourceDao.getName(doctorName);
    }

    @Override
    public int insertNum(TbNatmehaSignalSource tbNatmehaSignalSource) {
        return tbNatmehaSignalSourceDao.insertSelective(tbNatmehaSignalSource);
    }

    @Override
    public TbNatmehaSignalSource selectByItemid(Integer itemid) {
        return this.tbNatmehaSignalSourceDao.selectByItemid(itemid);
    }

    @Override
    public int updateNum(TbNatmehaSignalSource tbNatmehaSignalSource) {
        return this.tbNatmehaSignalSourceDao.updateNum(tbNatmehaSignalSource);
    }

    @Override
    public int deleteNum(int itemid) {
        return  this.tbNatmehaSignalSourceDao.deleteNum(itemid);
    }
}
