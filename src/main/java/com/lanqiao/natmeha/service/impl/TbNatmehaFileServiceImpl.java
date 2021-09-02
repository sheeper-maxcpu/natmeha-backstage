
package com.lanqiao.natmeha.service.Impl;

import com.lanqiao.natmeha.dao.TbNatmehaFileDao;
import com.lanqiao.natmeha.dao.TbNatmehaFileMapper;


import com.lanqiao.natmeha.dao.TbNatmehaFileDao;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author barry
 * @create 2021-09-01 20:59
 */
@Service("TbNatmehaFileService")
public class TbNatmehaFileServiceImpl implements TbNatmehaFileService {

    @Resource
    private TbNatmehaFileMapper tbNatmehaFileMapper;

    @Autowired
    private TbNatmehaFileDao tbNatmehaFileDao;

    /**
     * @param tbNatmehaFile
     * @return 向文件表里面添加文件
     */
    @Override
    public int insert(TbNatmehaFile tbNatmehaFile) {
        return this.tbNatmehaFileDao.insert(tbNatmehaFile);
    }

    //更改图片
    @Override
    public int updataFile(TbNatmehaFile tbNatmehaFile) {
        return this.tbNatmehaFileDao.updateByTbNatmehaFile(tbNatmehaFile);
    }

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
        return this.tbNatmehaFileMapper.insertFile(record);
    }

    @Override
    public TbNatmehaFile selectByPrimaryKey(Long id) {
        return this.tbNatmehaFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.insertFile(record);
    }

    @Override
    public int updateByPrimaryKey(TbNatmehaFile record) {
        return this.tbNatmehaFileMapper.insertFile(record);
    }

    @Override
    public int updateFile(String itemcode, String fileName) {
        return this.tbNatmehaFileMapper.updateFile(itemcode,fileName);
    }
}
