package com.lanqiao.natmeha.service.impl;

import com.lanqiao.natmeha.mapper.TbNatmehaFileMapper;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import com.lanqiao.natmeha.service.TbNatmehaHotspotService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author barry
 * @create 2021-08-27 16:01
 */
@Service("TbNatmehaFileService")
public class TbNatmehaFileServiceImpl implements TbNatmehaFileService {

    /**
     * 服务对象
     */
    @Resource
    private TbNatmehaFileMapper tbNatmehaFileMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return this.tbNatmehaFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertFile(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.insertFile(record);
    }

    @Override
    public int insertSelective(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.insertSelective(record);
    }

    @Override
    public TbNatmehaFile selectByPrimaryKey(Long id) {
        return this.tbNatmehaFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateFile(String itemcode, String fileName) {
        return  this.tbNatmehaFileMapper.updateFile(itemcode,fileName);
    }

}
