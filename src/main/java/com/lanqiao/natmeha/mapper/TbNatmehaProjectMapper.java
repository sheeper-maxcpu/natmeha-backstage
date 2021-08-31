package com.lanqiao.natmeha.mapper;

import com.lanqiao.natmeha.model.TbNatmehaProject;

/**
 * @Entity com.lanqiao.natmeha.model.TbNatmehaProject
 */
public interface TbNatmehaProjectMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TbNatmehaProject record);

    int insertSelective(TbNatmehaProject record);

    TbNatmehaProject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaProject record);

    int updateByPrimaryKey(TbNatmehaProject record);

}
