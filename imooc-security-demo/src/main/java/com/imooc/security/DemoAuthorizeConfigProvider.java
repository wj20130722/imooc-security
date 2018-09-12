package com.imooc.security;

import com.imooc.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/8/2.
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider{

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config  .antMatchers("/user/regist").permitAll() //社交登录 注册链接
                .antMatchers("/demo.html").hasRole("ADMIN");
//        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        return true;
    }
}
