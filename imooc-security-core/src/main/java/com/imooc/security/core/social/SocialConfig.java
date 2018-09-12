package com.imooc.security.core.social;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.social.support.ImoocSpringSocialConfigurer;
import com.imooc.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by wangjie on 2018/7/14.
 */
@Configuration
@EnableSocial
@Order(1) //当存在多个同类型的配置类的时候必须指定配置加载的优先级 否则UsersConnectionRepository获取的是系统默认的
public class SocialConfig extends SocialConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;



    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("imooc_");
        //设置connectionSignUp的实现可以实现无注册登陆流程
        if(connectionSignUp!=null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig(){
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
        //无法找到userid的时候需要跳转的注册页面
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    //解决注册过程中获取提供方的用户信息以及将userid与获取的提供方的用户信息绑定入库
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }
}
