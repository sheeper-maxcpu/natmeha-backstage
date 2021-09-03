package com.lanqiao.natmeha.dao;


import com.lanqiao.natmeha.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {


    Role selectByPrimaryKey(@Param("itemid") Long itemid);

    List<Role> getUserRoleByRoleType(@Param("roleType") Integer roleType);
}