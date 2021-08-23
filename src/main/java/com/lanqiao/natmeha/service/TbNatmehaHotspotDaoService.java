package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.model.TbNatmehaHotspotKey;

public interface TbNatmehaHotspotDaoService {

    /**
     * @param tbNatmehaHotspot
     * @return
     * 分页查询信息
     */
    Page<TbNatmehaHotspot> selectByPage(TbNatmehaHotspot tbNatmehaHotspot,int pageNum,int pageSize);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 科员添加新的信息
     */
    public int solar_insert_code(TbNatmehaHotspot tbNatmehaHotspot);

    /**
     * @param itemid
     * @return
     * 通过itemID来查看信息
     */
    public TbNatmehaHotspot solar_select(int itemid);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 通过itemID来修改数据
     */
    public int solar_updata_code(TbNatmehaHotspot tbNatmehaHotspot);

    /**
     * @param itemid
     * @return
     * 通过itemID来删除信息
     */
    public int deleteByItemid(int itemid);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 县级机构分页查询信息
     */
    Page<TbNatmehaHotspot> county_selectByPage(TbNatmehaHotspot tbNatmehaHotspot,int pageNum,int pageSize);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 市级机构分页查询信息
     */
    Page<TbNatmehaHotspot> municipal_selectByPage(TbNatmehaHotspot tbNatmehaHotspot,int pageNum,int pageSize);

    /**
     * @param tbNatmehaHotspot
     * @return
     * 省级机构分页查询信息
     */
    Page<TbNatmehaHotspot> provincial_selectByPage(TbNatmehaHotspot tbNatmehaHotspot,int pageNum,int pageSize);
}