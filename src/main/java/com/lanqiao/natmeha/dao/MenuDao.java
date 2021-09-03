package com.lanqiao.natmeha.dao;


import com.lanqiao.natmeha.model.Menu;
import com.lanqiao.natmeha.model.Role;

import java.util.List;

/**
 * @Entity com.lanqiao.tutorials.model.Menu
 */
public interface MenuDao {

    // int deleteByPrimaryKey(Long id);

    // int insert(Menu record);

    // int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    // int updateByPrimaryKeySelective(Menu record);

    // int updateByPrimaryKey(Menu record);

    List<Menu> getRoleMenuByRoles(List<Role> roles);
}




