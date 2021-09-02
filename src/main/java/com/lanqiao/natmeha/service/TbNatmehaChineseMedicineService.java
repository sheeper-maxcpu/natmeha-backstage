package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaChineseMedicine;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Param;

/**
 * @author barry
 * @create 2021-08-31 22:53
 */
public interface TbNatmehaChineseMedicineService {
    int deleteByPrimaryKey(Long id);

    int insertTCMCommon(TbNatmehaChineseMedicine record);

    int insertSelective(TbNatmehaChineseMedicine record);

    TbNatmehaChineseMedicine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaChineseMedicine record);

    int updateByPrimaryKey(TbNatmehaChineseMedicine record);

    Page<TbNatmehaChineseMedicine> selectAllByCountry( TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                      Integer pageNum,
                                                      Integer pageSize);
    TbNatmehaChineseMedicine queryById(Integer itemid);

    int deleteById(Integer itemid);

    Page<TbNatmehaChineseMedicine> selectAllByPageForCountry(TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                     Integer pageNum,
                                                     Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByCity( TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                       Integer pageNum,
                                                       Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageForCity(TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                             Integer pageNum,
                                                             Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByProvince( TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                    Integer pageNum,
                                                    Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageForProvince(TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                          Integer pageNum,
                                                          Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageFour(TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                              Integer pageNum,
                                                              Integer pageSize);
}
