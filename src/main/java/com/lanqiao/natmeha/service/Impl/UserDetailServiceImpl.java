package com.lanqiao.natmeha.service.Impl;

import com.lanqiao.natmeha.dao.MenuDao;
import com.lanqiao.natmeha.dao.RoleDao;
import com.lanqiao.natmeha.dao.UserDao;
import com.lanqiao.natmeha.model.Menu;
import com.lanqiao.natmeha.model.Role;
import com.lanqiao.natmeha.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @Author:YangZiYang
 * @create 2021/9/3 10:27
 */
@Service("userDetailsService")
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            List<Role> roles = roleDao.getUserRoleByRoleType(user.getType());
            Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            List<Menu> menus = menuDao.getRoleMenuByRoles(roles);

            User user1 = new User();
            user1.setItemid(user.getItemid());
            user1.setUsername(user.getUsername());
            user1.setName(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setUserRoles(roles);
            user1.setRoleMenus(menus);
            user1.setAuthorities(authorities);
            return user1;
        }else {
            log.debug(username + "not found");
            throw new UsernameNotFoundException(username +" not found");
        }
    }
}
