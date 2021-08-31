package com.lanqiao.natmeha.service.impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.mapper.TbNatmehaHotspotMapper;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaHotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author barry
 * @create 2021-08-27 15:58
 */
@Service("TbNatmehaHotspotService")
public class TbNatmehaHotspotServiceImpl implements TbNatmehaHotspotService {

    /**
     * 服务对象
     */
    @Resource
    private TbNatmehaHotspotMapper tbNatmehaHotspotMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return this.tbNatmehaHotspotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertTCMCulture(TbNatmehaHotspot record) {
        return this.tbNatmehaHotspotMapper.insertTCMCulture(record);
    }

    @Override
    public int insertSelective(TbNatmehaHotspot record) {
        return this.tbNatmehaHotspotMapper.insertSelective(record);
    }

    @Override
    public TbNatmehaHotspot selectByPrimaryKey(Long id) {
        return this.tbNatmehaHotspotMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TbNatmehaHotspot record) {
        return this.tbNatmehaHotspotMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TbNatmehaHotspot record) {
        return this.tbNatmehaHotspotMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPage(String message, String dataType,Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPage(message,dataType,pageNum,pageSize);
    }

    @Override
    public TbNatmehaHotspot queryById(Integer itemid) {
        return this.tbNatmehaHotspotMapper.queryById(itemid);
    }

    @Override
    public TbNatmehaHotspot queryByIdAndCode(Integer itemid,String itemcode) {
        return this.tbNatmehaHotspotMapper.queryByIdAndCode(itemid,itemcode);
    }

    @Override
    public int deleteById(Integer itemid) {
        return this.tbNatmehaHotspotMapper.deleteById(itemid);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageForCountry(String message,String dataType,Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageForCountry(message,dataType,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageTwo(String message,String dataType, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageTwo(message,dataType,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageForCity(String message,String dataType, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageForCity(message,dataType,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageThree(String message,String dataType, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageThree(message,dataType,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageForProvince(String message,String dataType, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageForProvince(message,dataType,pageNum,pageSize);
    }

    @Override
    public Page<TbNatmehaHotspot> selectAllByPageFour(String message,String dataType, Integer pageNum, Integer pageSize) {
        return this.tbNatmehaHotspotMapper.selectAllByPageFour(message,dataType,pageNum,pageSize);
    }

}
