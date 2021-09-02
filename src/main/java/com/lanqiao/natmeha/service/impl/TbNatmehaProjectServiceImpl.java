package com.lanqiao.natmeha.service.Impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.dao.TbNatmehaProjectDao;
import com.lanqiao.natmeha.model.TbNatmehaProject;
import com.lanqiao.natmeha.service.TbNatmehaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("TbNatmehaProjectService")
public class TbNatmehaProjectServiceImpl implements TbNatmehaProjectService {

    @Autowired
    TbNatmehaProjectDao tbNatmehaProjectDao;

    /**
     * @param tbNatmehaProject
     * @return
     * 分页查询信息
     */
    public Page<TbNatmehaProject> selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize){
        return this.tbNatmehaProjectDao.selectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @return 科员添加新的信息
     */
    @Override
    public int solar_insert_code(TbNatmehaProject tbNatmehaProject) {
        return this.tbNatmehaProjectDao.solar_insert_code(tbNatmehaProject);
    }

    /**
     * @param itemid
     * @return 通过itemID来查看信息
     */
    @Override
    public TbNatmehaProject solar_select(int itemid) {
        return this.tbNatmehaProjectDao.solar_select(itemid);
    }

    /**
     * @param tbNatmehaProject
     * @return 通过itemID来修改数据
     */
    @Override
    public int solar_updata_code(TbNatmehaProject tbNatmehaProject) {
        return this.tbNatmehaProjectDao.solar_updata_code(tbNatmehaProject);
    }

    /**
     * @param itemid
     * @return 通过itemID来删除信息
     */
    @Override
    public int deleteByItemid(int itemid) {
        return this.tbNatmehaProjectDao.deleteByItemid(itemid);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 县级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> county_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.county_selectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 市级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> municipal_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.municipal_selectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 省级机构分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> provincial_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.provincial_selectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 市级管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> municipal_clerkselectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.municipal_clerkselectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 省级管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> provincial_clerk_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.provincial_clerk_selectByPage(tbNatmehaProject,pageNum,pageSize);
    }

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return 国医堂管理员分页查询信息
     */
    @Override
    public Page<TbNatmehaProject> natmehaselectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize) {
        return this.tbNatmehaProjectDao.natmehaselectByPage(tbNatmehaProject,pageNum,pageSize);
    }

}