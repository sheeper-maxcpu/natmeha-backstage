package com.lanqiao.natmeha.service.Impl;

import com.lanqiao.natmeha.dao.TbNatmehaFileDao;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 封剑武
 * @date 2021/8/24 18:01
 */
@Service("TbNatmehaFileService")
public class TbNatmehaFileServiceImpl implements TbNatmehaFileService {

    @Autowired
    TbNatmehaFileDao tbNatmehaFileDao;
    /**
     * @param tbNatmehaFile
     * @return 向文件表里面添加文件
     */
    @Override
    public int insert(TbNatmehaFile tbNatmehaFile) {
        return this.tbNatmehaFileDao.insert(tbNatmehaFile);
    }
}
