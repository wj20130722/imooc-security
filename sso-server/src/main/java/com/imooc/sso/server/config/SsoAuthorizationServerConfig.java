package com.imooc.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by wangjie on 2018/7/30.
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private AuthenticationManager authenticationManager;

//    @Autowired
//    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore())/*.authenticationManager(authenticationManager).userDetailsService(userDetailsService)*/
                .accessTokenConverter(jwtAccessTokenConverter())
                /*.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)*/;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("imooc1")
                .secret("imoocsecret1")
//                .resourceIds("imooc1")
                .accessTokenValiditySeconds(7200) //token有效时间 单位秒 设置两小时
                .authorizedGrantTypes("authorization_code","password","refresh_token")//设置服务支持的授权模式
                //.authorities("ROLE_USER")
                .scopes("all") //设置客户端访问权限 --客户端可以不写默认使用配置权限 如果带了参数必须是配置的否则出错
                .redirectUris("http://127.0.0.1:8080/client1/login")
        .and()
                .withClient("imooc2")
                .secret("imoocsecret2")
//                .resourceIds("imooc2")
                .accessTokenValiditySeconds(7200) //token有效时间 单位秒 设置两小时
                .authorizedGrantTypes("authorization_code","password","refresh_token")//设置服务支持的授权模式
                //.authorities("ROLE_USER")
                .scopes("all") //设置客户端访问权限 --客户端可以不写默认使用配置权限 如果带了参数必须是配置的否则出错
                .redirectUris("http://127.0.0.1:8060/client2/login");
//                .autoApprove(true)  //用户授权默认同意设置
//                .autoApprove("get_user_info");
    }

    /**
     * 设置client-secret使用的passwordEncoder
     * sso客户端获取jwt的加密key需要认证通过才能获取
     * sso客户端配置信息：client-id,client-secret支持表单方式获取 默认支持basic方式获取
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //应用获取jwt的签名key需要认证授权才能获取
        security.passwordEncoder(NoOpPasswordEncoder.getInstance())
                .tokenKeyAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //token生成处理
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("imooc");
        return converter;
    }
}
