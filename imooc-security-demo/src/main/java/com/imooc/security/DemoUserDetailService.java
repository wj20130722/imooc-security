package com.imooc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/7/4.
 */
@Slf4j
@Component
public class DemoUserDetailService implements UserDetailsService,SocialUserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    //表单登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名:{}",username);
        //TODO 根据用户名查找用户信息

        return buildUser(username);
    }

    //社交登录
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户名:{}",userId);
        //TODO 根据用户名查找用户信息
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId){
        //TODO 根据查找的用户信息判断是否被冻结
        String password = passwordEncoder.encode("123");
        log.info("数据库用户名密码：{}",password);
        return new SocialUser(userId,password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN"));
    }
}
