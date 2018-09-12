package com.imooc.security.core.authorize;

import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/8/1.
 */
@Component
@Order(Integer.MIN_VALUE)
public class ImoocAuthorizeConfigProvider implements AuthorizeConfigProvider{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, //短信验证码授权登录获取授权token
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*", //短信验证码发送url
                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, //openid授权登录获取token
                securityProperties.getBrowser().getSignUpUrl(),
                SecurityConstants.DEFAULT_SOCIAL_SIGN_UP_URL, //社交登录注册url
                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                securityProperties.getBrowser().getSignOutUrl(),
                "/error",
                "/favicon.ico").permitAll();
        return true;
    }
}
