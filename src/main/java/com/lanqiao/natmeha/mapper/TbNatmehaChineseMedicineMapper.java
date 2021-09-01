package com.lanqiao.natmeha.mapper;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaChineseMedicine;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.lanqiao.natmeha.model.TbNatmehaChineseMedicine
 */
public interface TbNatmehaChineseMedicineMapper {

    int deleteByPrimaryKey(Long id);

    int insertTCMCommon(TbNatmehaChineseMedicine record);

    int insertSelective(TbNatmehaChineseMedicine record);

    TbNatmehaChineseMedicine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaChineseMedicine record);

    int updateByPrimaryKey(TbNatmehaChineseMedicine record);

    Page<TbNatmehaChineseMedicine> selectAllByCountry(@Param("tbNatmehaChineseMedicine") TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                      @Param("pageNum") Integer pageNum,
                                                      @Param("pageSize") Integer pageSize);

    TbNatmehaChineseMedicine queryById(@Param("itemid") Integer itemid);

    int deleteById(@Param("itemid") Integer itemid);

    Page<TbNatmehaChineseMedicine> selectAllByPageForCountry(@Param("tbNatmehaChineseMedicine") TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                     @Param("pageNum") Integer pageNum,
                                                     @Param("pageSize") Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageForCity(@Param("tbNatmehaChineseMedicine") TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                             @Param("pageNum") Integer pageNum,
                                                             @Param("pageSize") Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageForProvince(@Param("tbNatmehaChineseMedicine") TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                          @Param("pageNum") Integer pageNum,
                                                          @Param("pageSize") Integer pageSize);

    Page<TbNatmehaChineseMedicine> selectAllByPageFour(@Param("tbNatmehaChineseMedicine") TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                                              @Param("pageNum") Integer pageNum,
                                                              @Param("pageSize") Integer pageSize);
}
