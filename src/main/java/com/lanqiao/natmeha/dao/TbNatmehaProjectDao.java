package com.lanqiao.natmeha.dao;


import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.model.TbNatmehaProject;
import org.apache.ibatis.annotations.Param;

public interface TbNatmehaProjectDao {
    /**
     * @param itemid
     * @return
     * 通过itemID来删除信息
     */
    public int deleteByItemid(int itemid);

    /**
     * @param tbNatmehaProject
     * @return
     * 添加新的信息
     */
    int solar_insert_code(TbNatmehaProject tbNatmehaProject);

    int insertSelective(TbNatmehaProject record);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 分页查询开展项目信息
     */
    Page<TbNatmehaProject> selectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param itemid
     * @return
     * 通过itemID来查看信息
     */
    public TbNatmehaProject solar_select(Integer itemid);

    /**
     * @param tbNatmehaProject
     * @return
     * 通过itemID来修改数据
     */
    int solar_updata_code(TbNatmehaProject tbNatmehaProject);

    int updateByPrimaryKey(TbNatmehaProject record);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 县级机构分页查询开展项目信息
     */
    Page<TbNatmehaProject> county_selectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 市级机构分页查询开展项目信息
     */
    Page<TbNatmehaProject> municipal_selectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 省级机构分页查询开展项目信息
     */
    Page<TbNatmehaProject> provincial_selectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);


    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 市级管理员分页查询节气养生信息
     */
    Page<TbNatmehaProject> municipal_clerkselectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 省级管理员分页查询节气养生信息
     */
    Page<TbNatmehaProject> provincial_clerk_selectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

    /**
     * @param tbNatmehaProject
     * @param pageNum
     * @param pageSize
     * @return
     * 主管理员发布页面
     */
    Page<TbNatmehaProject> natmehaselectByPage(
            @Param("tbNatmehaProject") TbNatmehaProject tbNatmehaProject,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize);

}
