package com.lanqiao.natmeha.service;

import com.lanqiao.natmeha.model.TbNatmehaFile;

/**
 * @auther 封剑武
 * @date 2021/8/24 18:00
 */
public interface TbNatmehaFileService {
    /**
     * @param tbNatmehaFile
     * @return
     * 向文件表里面添加文件
     */
    int insert(TbNatmehaFile tbNatmehaFile);

    public int updataFile(TbNatmehaFile tbNatmehaFile);

    int deleteByPrimaryKey(Long id);

    int insertFile(TbNatmehaFile record);

    int insertSelective(TbNatmehaFile record);

    TbNatmehaFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaFile record);

    int updateByPrimaryKey(TbNatmehaFile record);

    int updateFile(String itemcode,String fileName);
}
