package com.imooc.security.app.config;

import com.imooc.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.authorize.AuthorizeConfigManager;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务配置
 * Created by wangjie on 2018/7/20.
 */
@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthentioncationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(imoocAuthentioncationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .permitAll()
                .and()
                //加载验证码(图形验证码和短信验证码)过滤器配置
                .apply(validateCodeSecurityConfig)
                .and()
                //加载短信验证码验证配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //加载社交登录相关配置
                .apply(imoocSocialSecurityConfig)
                .and()
                //APP端通过获取的openId获取访问令牌 ---APP端三方登录简化模式
                .apply(openIdAuthenticationSecurityConfig)
                .and()
               /* .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, //短信验证码授权登录获取授权token
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"*//*", //短信验证码发送url
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, //openid授权登录获取token
                        SecurityConstants.DEFAULT_SOCIAL_SIGN_UP_URL, //社交登录注册url
                        "/user/regist",
                        "/error",
                        "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()*/
//                .httpBasic()
                .csrf()
                .disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
