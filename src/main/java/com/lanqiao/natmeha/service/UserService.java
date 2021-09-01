package com.lanqiao.natmeha.service;

import com.lanqiao.natmeha.model.Organization;
import com.lanqiao.natmeha.model.User;

/**
*
*/
public interface UserService {

    //登录
    public User selectForLogin(User user);

    //注册
    void insertForReg(User user);

//    void insertForRegOrg(Organization organization);
}
