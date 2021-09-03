package com.lanqiao.natmeha.config;

import com.lanqiao.natmeha.handle.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author:YangZiYang
 * @create 2021/9/3 11:20
 */
@Configurable
@EnableWebSecurity
//开启 Spring Security 方法级安全注解 @EnableGlobalMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 静态资源设置
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        //不拦截静态资源,所有用户均可访问的资源
        webSecurity.ignoring().antMatchers(
                "/",
                "/component/**",
                "/css/**",
                "/datapicker/**",
                "/datetimepicker/**",
                "/js/**",
                "/images/**",
                "/layer/**",
                "/layui/**",
                "/quixlab/**",
                "/style/**",
                "/wangEditor/**"
        );
    }

    /**
     * http请求设置
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //注释就是使用 csrf 功能
        http.headers().frameOptions().disable();//解决 in a frame because it set 'X-Frame-Options' to 'DENY' 问题
        http.authorizeRequests()
                .antMatchers("/user/login",
                        "/initUserData")
                .permitAll()
                .anyRequest()
                .access("@rbacPermission.hasPermission(request, authentication)") //根据账号权限访问
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout");
    }

    /**
     * 自定义获取用户信息接口
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 密码加密算法
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder noOpPasswordEncoder =  NoOpPasswordEncoder.getInstance();
        return noOpPasswordEncoder;
        // return new BCryptPasswordEncoder();
    }
}
