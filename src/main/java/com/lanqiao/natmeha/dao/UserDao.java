package com.lanqiao.natmeha.dao;

import com.lanqiao.natmeha.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @Entity com.lanqiao.natmeha.model.User
*/
@Mapper
public interface UserDao {

    //登录
    User selectForLogin(User user);

    //注册
    void insertForReg(User user);

//    void insertForRegOrg(Organization organization);


}
