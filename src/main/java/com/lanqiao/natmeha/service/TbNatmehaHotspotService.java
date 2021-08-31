package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Param;

/**
 * @author barry
 * @create 2021-08-27 15:57
 */
public interface TbNatmehaHotspotService {
    int deleteByPrimaryKey(Long id);

    int insertTCMCulture(TbNatmehaHotspot record);

    int insertSelective(TbNatmehaHotspot record);

    TbNatmehaHotspot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaHotspot record);

    int updateByPrimaryKey(TbNatmehaHotspot record);

    Page<TbNatmehaHotspot> selectAllByPage(String message,
                                           String dataType,
                                           Integer pageNum,
                                           Integer pageSize);

    TbNatmehaHotspot queryById(Integer itemid);

    TbNatmehaHotspot queryByIdAndCode(Integer itemid,String itemcode);

    int deleteById(@Param("itemid") Integer itemid);

    Page<TbNatmehaHotspot> selectAllByPageForCountry(String message,
                                                     String dataType,
                                                     Integer pageNum,
                                                     Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageTwo(String message,
                                              String dataType,
                                              Integer pageNum,
                                              Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageForCity(String message,
                                                  String dataType,
                                                  Integer pageNum,
                                                  Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageThree(String message,
                                                String dataType,
                                                  Integer pageNum,
                                                  Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageForProvince(String message,
                                                      String dataType,
                                                  Integer pageNum,
                                                  Integer pageSize);
    Page<TbNatmehaHotspot> selectAllByPageFour(String message,
                                               String dataType,
                                                Integer pageNum,
                                                Integer pageSize);
}
