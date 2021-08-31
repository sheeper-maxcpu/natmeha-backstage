package com.lanqiao.natmeha.mapper;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.lanqiao.natmeha.model.TbNatmehaHotspot
 */
public interface TbNatmehaHotspotMapper {

    int deleteByPrimaryKey(Long id);

    int insertTCMCulture(TbNatmehaHotspot record);

    int insertSelective(TbNatmehaHotspot record);

    TbNatmehaHotspot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbNatmehaHotspot record);

    int updateByPrimaryKey(TbNatmehaHotspot record);

    Page<TbNatmehaHotspot> selectAllByPage(@Param("message") String message,
                                           @Param("dataType") String dataType,
                                           @Param("pageNum") Integer pageNum,
                                           @Param("pageSize") Integer pageSize);

    TbNatmehaHotspot queryById(@Param("itemid") Integer itemid);

    TbNatmehaHotspot queryByIdAndCode(@Param("itemid") Integer itemid,
                                      @Param("itemcode") String itemcode);

    int deleteById(@Param("itemid") Integer itemid);

    /**
     * @param message
     * @param pageNum
     * @param pageSize
     * @return  县级部门审核列表
     */
    Page<TbNatmehaHotspot> selectAllByPageForCountry(@Param("message") String message,
                                                     @Param("dataType") String dataType,
                                                     @Param("pageNum") Integer pageNum,
                                                     @Param("pageSize") Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageTwo(@Param("message") String message,
                                              @Param("dataType") String dataType,
                                              @Param("pageNum") Integer pageNum,
                                              @Param("pageSize") Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageForCity(@Param("message") String message,
                                                  @Param("dataType") String dataType,
                                                  @Param("pageNum") Integer pageNum,
                                                  @Param("pageSize") Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageThree(@Param("message") String message,
                                                @Param("dataType") String dataType,
                                                  @Param("pageNum") Integer pageNum,
                                                  @Param("pageSize") Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageForProvince(@Param("message") String message,
                                                      @Param("dataType") String dataType,
                                                  @Param("pageNum") Integer pageNum,
                                                  @Param("pageSize") Integer pageSize);

    Page<TbNatmehaHotspot> selectAllByPageFour(@Param("message") String message,
                                               @Param("dataType") String dataType,
                                                @Param("pageNum") Integer pageNum,
                                                @Param("pageSize") Integer pageSize);
}
