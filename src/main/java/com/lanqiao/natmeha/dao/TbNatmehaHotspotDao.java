package com.lanqiao.natmeha.dao;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import org.apache.ibatis.annotations.Param;

public interface TbNatmehaHotspotDao {
    /**
     * @param itemid
     * @return
     * 通过itemID来删除信息
     */
    public int deleteByItemid(int itemid);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 添加新的信息
     */
    int solar_insert_code(TbNatmehaHotspot tbNatmehaHotspot);

    int insertSelective(TbNatmehaHotspot record);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> selectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);
    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 主管理员发布页面
     */
    Page<TbNatmehaHotspot> mainselectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param itemid
     * @return
     * 通过itemID来查看信息
     */
    public TbNatmehaHotspot solar_select(Integer itemid);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 通过itemID来修改数据
     */
    int solar_updata_code(TbNatmehaHotspot tbNatmehaHotspot);

    int updateByPrimaryKey(TbNatmehaHotspot record);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 县级机构分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> county_selectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 市级机构分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> municipal_selectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 省级机构分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> provincial_selectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 市级管理员分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> municipal_clerkselectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return
     * 省级管理员分页查询节气养生信息
     */
    Page<TbNatmehaHotspot> provincial_clerk_selectByPage(
            @Param("tbNatmehaHotspot") TbNatmehaHotspot tbNatmehaHotspot,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

}