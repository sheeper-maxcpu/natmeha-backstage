package com.lanqiao.natmeha.service.Impl;

import com.lanqiao.natmeha.dao.TbFileDao;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.service.TbFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:YangZiYang
 * @create 2021/8/28 9:47
 */
@Service("TbFileService")
public class TbFileServiceImpl implements TbFileService {
    @Autowired
    TbFileDao tbFileDao;

    @Override
    public int insert(TbNatmehaFile tbNatmehaFile) {
        return tbFileDao.insert(tbNatmehaFile);
    }

    @Override
    public TbNatmehaFile selectFile(String dateCode) {
        return this.tbFileDao.selectFile(dateCode);
    }

    @Override
    public int updateFilePath(TbNatmehaFile tbNatmehaFile) {
        return this.tbFileDao.updateFilePath(tbNatmehaFile);
    }

}
