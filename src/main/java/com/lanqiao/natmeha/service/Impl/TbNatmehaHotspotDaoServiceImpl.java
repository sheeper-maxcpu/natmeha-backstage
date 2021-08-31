package com.lanqiao.natmeha.service.Impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.dao.TbNatmehaHotspotDao;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaHotspotDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbnatmehahotspotdaoservice")
public class TbNatmehaHotspotDaoServiceImpl implements TbNatmehaHotspotDaoService {
    @Autowired
    private TbNatmehaHotspotDao tbNatmehaHotspotDao;

    /**
     * @param tbNatmehaHotspot
     * @return
     * 分页查询信息
     */
    public Page<TbNatmehaHotspot> selectByPage(TbNatmehaHotspot tbNatmehaHotspot,int pageNum,int pageSize){
        return this.tbNatmehaHotspotDao.selectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 主管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> mainselectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.mainselectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @return 科员添加新的信息
     */
    @Override
    public int solar_insert_code(TbNatmehaHotspot tbNatmehaHotspot) {
        return this.tbNatmehaHotspotDao.solar_insert_code(tbNatmehaHotspot);
    }

    /**
     * @param itemid
     * @return 通过itemID来查看信息
     */
    @Override
    public TbNatmehaHotspot solar_select(int itemid) {
        return this.tbNatmehaHotspotDao.solar_select(itemid);
    }

    /**
     * @param tbNatmehaHotspot
     * @return 通过itemID来修改数据
     */
    @Override
    public int solar_updata_code(TbNatmehaHotspot tbNatmehaHotspot) {
        return this.tbNatmehaHotspotDao.solar_updata_code(tbNatmehaHotspot);
    }

    /**
     * @param itemid
     * @return 通过itemID来删除信息
     */
    @Override
    public int deleteByItemid(int itemid) {
        return this.tbNatmehaHotspotDao.deleteByItemid(itemid);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 县级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> county_selectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.county_selectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 市级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> municipal_selectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.municipal_selectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 省级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> provincial_selectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.provincial_selectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 市级管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> municipal_clerkselectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.municipal_clerkselectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaHotspot
     * @param pageNum
     * @param pageSize
     * @return 省级管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaHotspot> provincial_clerk_selectByPage(TbNatmehaHotspot tbNatmehaHotspot, int pageNum, int pageSize) {
        return this.tbNatmehaHotspotDao.provincial_clerk_selectByPage(tbNatmehaHotspot,pageNum,pageSize);
    }

}