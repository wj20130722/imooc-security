package com.imooc.security.browser.config;

import com.imooc.security.browser.session.ImoocExpiredSessionStrategy;
import com.imooc.security.core.authentication.AbstractChannelSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.authorize.AuthorizeConfigManager;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.config.ValidateCodeSecurityConfig;
import com.imooc.security.core.validate.code.filter.SmsCodeFilterBAK;
import com.imooc.security.core.validate.code.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by wangjie on 2018/7/2.
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    //remeberme token持久化存储配置
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动的时候初始化默认的sql脚本
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //加载用户名和密码登录配置
        applyPasswordAuthenticationConfig(http);
        http
                //加载验证码(图形验证码和短信验证码)过滤器配置
                .apply(validateCodeSecurityConfig)
                .and()
                //加载短信验证码验证配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //加载社交登录相关配置
                .apply(imoocSocialSecurityConfig)
               /* .and()
                .authorizeRequests()
                    .antMatchers(securityProperties.getBrowser().getLoginPage(),
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"*//*",
                            securityProperties.getBrowser().getSignUpUrl(),
                            "/user/regist",
                            //  "/social/user",
                            securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                            securityProperties.getBrowser().getSignOutUrl(),
                            "/error",
                            "/favicon.ico").permitAll()
                    // .access("hasRole('ADMIN') and hasIpAddress('127.0.0.1')")
                    //.antMatchers(HttpMethod.GET,"/user*//*").hasRole("ADMIN") //.hasAuthority("ROLE_ADMIN") 完全匹配
                    .anyRequest().authenticated()*/
                .and()
//                .httpBasic()
                .rememberMe()
                //将与用户相关的token信息存放在数据库中,cookie里面存放的和用户信息无关的token信息
                //默认的实现是把用户敏感的信息通过加密方式存放在cookie中
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRemeberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                .sessionManagement()
                    //.invalidSessionUrl("/session/invalid")
                    .invalidSessionStrategy(invalidSessionStrategy) //session过期失效的策略
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) //session并发控制 同一个用户重复登录 后面登录会将前面登录的session失效掉
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin()) //禁止后续的重复登录行为
                    .expiredSessionStrategy(sessionInformationExpiredStrategy) //session并发导致失效处理策略
                .and()
                .and()
                    .logout() //退出操作 1.清除session 2.输出remeberme对应的cookie 3.清除授权信息 4.跳转到登录页面
                        .logoutUrl("/signOut")
                        //.logoutSuccessUrl("/imooc-logout.html")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .deleteCookies("JSESSIONID") //清除JSESSIIONID
                .and()
                .csrf()
                    .disable();
                //加载各模块访问权限配置
                authorizeConfigManager.config(http.authorizeRequests());



        //添加验证码处理过滤器
       /* ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilterBAK smsCodeFilterBAK = new SmsCodeFilterBAK();
        smsCodeFilterBAK.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        smsCodeFilterBAK.setSecurityProperties(securityProperties);
        smsCodeFilterBAK.afterPropertiesSet();
        http
                .addFilterBefore(smsCodeFilterBAK, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);*/



    }

    /*@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/



}
