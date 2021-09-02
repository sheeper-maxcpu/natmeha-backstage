package com.lanqiao.natmeha.service;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.model.TbNatmehaProject;

/**
 * 项目记录表(TbNatmehaProject)表服务接口
 *
 * @author makejava
 * @since 2021-07-24 09:12:33
 */
public interface TbNatmehaProjectService {

    /**
     * @param tbNatmehaProject
     * @return
     * 分页查询信息
     */
    Page<TbNatmehaProject> selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 科员添加新的信息
     */
    public int solar_insert_code(TbNatmehaProject tbNatmehaProject);

    /**
     * @param itemid
     * @return
     * 通过itemID来查看信息
     */
    public  TbNatmehaProject solar_select(int itemid);

    /**
     * @param tbNatmehaProject
     * @return
     * 通过itemID来修改数据
     */
    public int solar_updata_code(TbNatmehaProject tbNatmehaProject);

    /**
     * @param itemid
     * @return
     * 通过itemID来删除信息
     */
    public int deleteByItemid(int itemid);

    /**
     * @param tbNatmehaProject
     * @return
     * 县级机构分页查询信息
     */
    Page< TbNatmehaProject> county_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 市级机构分页查询信息
     */
    Page< TbNatmehaProject> municipal_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 省级机构分页查询信息
     */
    Page< TbNatmehaProject> provincial_selectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 市级管理员分页查询信息
     */
    Page<TbNatmehaProject> municipal_clerkselectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 省级管理员分页查询信息
     */
    Page<TbNatmehaProject> provincial_clerk_selectByPage(TbNatmehaProject tbNatmehaProject,int pageNum,int pageSize);

    /**
     * @param tbNatmehaProject
     * @return
     * 国医堂管理员分页查询信息
     */
    Page<TbNatmehaProject> natmehaselectByPage(TbNatmehaProject tbNatmehaProject, int pageNum, int pageSize);

}