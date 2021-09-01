package com.lanqiao.natmeha.service.impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.mapper.TbNatmehaChineseMedicineMapper;
import com.lanqiao.natmeha.model.TbNatmehaChineseMedicine;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaChineseMedicineService;
import com.lanqiao.natmeha.service.TbNatmehaHotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author barry
 */
@Service("TbNatmehaChineseMedicineService")
public class TbNatmehaChineseMedicineServiceImpl implements TbNatmehaChineseMedicineService {
    /**
     * 服务对象
     */
    @Resource
    private TbNatmehaChineseMedicineMapper tbNatmehaChineseMedicineMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return this.tbNatmehaChineseMedicineMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertTCMCommon(TbNatmehaChineseMedicine record) {
        return this.tbNatmehaChineseMedicineMapper.insertTCMCommon(record);
    }

    @Override
    public int insertSelective(TbNatmehaChineseMedicine record) {
        return this.tbNatmehaChineseMedicineMapper.insertSelective(record);
    }

    @Override
    public TbNatmehaChineseMedicine selectByPrimaryKey(Long id) {
        return this.tbNatmehaChineseMedicineMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TbNatmehaChineseMedicine record) {
        return this.tbNatmehaChineseMedicineMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TbNatmehaChineseMedicine record) {
        return this.tbNatmehaChineseMedicineMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<TbNatmehaChineseMedicine> selectAllByCountry(TbNatmehaChineseMedicine tbNatmehaChineseMedicine,Integer pageNum, Integer pageSize) {
        return this.tbNatmehaChineseMedicineMapper.selectAllByCountry(tbNatmehaChineseMedicine,pageNum,pageSize);
    }

    @Override
    public TbNatmehaChineseMedicine queryById(Integer itemid) {
        return this.tbNatmehaChineseMedicineMapper.queryById(itemid);
    }

    @Override
    public int deleteById(Integer itemid) {
        return this.tbNatmehaChineseMedicineMapper.deleteById(itemid);
    }

    @Override
    public Page<TbNatmehaChineseMedicine> selectAllByPageForCountry(TbNatmehaChineseMedicine tbNatmehaChineseMedicine, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaChineseMedicineMapper.selectAllByPageForCountry(tbNatmehaChineseMedicine,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaChineseMedicine> selectAllByPageForCity(TbNatmehaChineseMedicine tbNatmehaChineseMedicine, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaChineseMedicineMapper.selectAllByPageForCity(tbNatmehaChineseMedicine,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaChineseMedicine> selectAllByPageForProvince(TbNatmehaChineseMedicine tbNatmehaChineseMedicine, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaChineseMedicineMapper.selectAllByPageForProvince(tbNatmehaChineseMedicine,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaChineseMedicine> selectAllByPageFour(TbNatmehaChineseMedicine tbNatmehaChineseMedicine, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaChineseMedicineMapper.selectAllByPageFour(tbNatmehaChineseMedicine,pageNum,pageSize);
    }


}
