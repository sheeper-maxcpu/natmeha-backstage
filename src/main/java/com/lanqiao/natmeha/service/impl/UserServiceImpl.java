package com.lanqiao.natmeha.service.Impl;

import com.lanqiao.natmeha.dao.UserDao;
import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
*
*/
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    //登录
    @Override
    public User selectForLogin(User user){
        return userDao.selectForLogin(user);
    }

    //注册
    @Override
    public void insertForReg(User user) {
        userDao.insertForReg(user);
    }

//    @Override
//    public void insertForRegOrg(Organization organization) {
//        userMapper.insertForRegOrg(organization);
//    }

}
