package com.imooc.security.core.authentication;

import com.imooc.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 用户名和密码登录配置
 * Created by wangjie on 2018/7/12.
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler imoocAuthentioncationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                    .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                    .successHandler(imoocAuthentioncationSuccessHandler)
                    .failureHandler(imoocAuthenticationFailureHandler)
                    .permitAll();
    }
}
