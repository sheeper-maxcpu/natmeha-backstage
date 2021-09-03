package com.lanqiao.natmeha.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 *
 * @TableName user
 */
@Data
public class User implements Serializable, UserDetails {
    /**
     *
     */
    private Integer itemid;

    /**
     * 唯一标识UUID
     */
    private String itemcode;

    /**
     * 机构id(关联organization表中的itemID）
     */
    private String orgCode;

    /**
     * 登陆账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证件类型
     */
    private String idcardType;

    /**
     * 证件号码
     */
    private String idcardNo;

    /**
     * email
     */
    private String email;

    /**
     *
     */
    private String state;

    /**
     * 电话号码
     */
    private String mobilephone;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date itemcreateat;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date itemupdateat;

    /**
     * 地级机构id
     */
    private String cityid;

    /**
     * 类型（0：普通，1：管理员）
     */
    private Integer type;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     *
     */
    private String wxOpenid;

    /**
     * 肖像
     */
    private byte[] portrait;

    // 用户角色集合
    private List<Role> userRoles;
    // 角色菜单集合
    private List<Menu> roleMenus;
    // 用户权限集合
    private Collection<? extends GrantedAuthority> authorities;


    private static final long serialVersionUID = 1L;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}