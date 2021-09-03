package com.lanqiao.natmeha.permission;

import com.lanqiao.natmeha.model.Menu;
import com.lanqiao.natmeha.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author:YangZiYang
 * @create 2021/9/3 10:47
 */
@Component("rbacPermission")
@Slf4j
public class RbacPermission {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        log.debug("RbacPermission中的 principal: {}", principal);
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
            User user = (User) principal;
            List<Menu> menus = user.getRoleMenus();
            log.debug("{} 访问的菜单数：{}", user.getUsername(), menus.size());
            for (Menu menu : menus) {
                if (antPathMatcher.match(menu.getMenuUrl(), request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    public boolean checkUrl(List<Menu> roleMenus, String url) {
        if (roleMenus != null) {
            for (Menu menu : roleMenus) {
                if (Objects.equals(menu.getMenuUrl(), url)) {
                    return true;
                }
            }
        }
        return false;
    }
}
