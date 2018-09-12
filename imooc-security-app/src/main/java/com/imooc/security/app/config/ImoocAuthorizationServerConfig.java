package com.imooc.security.app.config;

import com.imooc.security.core.properties.OAuth2ClientProperties;
import com.imooc.security.core.properties.OAuth2Properties;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 * Created by wangjie on 2018/7/20.
 */
@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;


    /**
     * JWT 配置
     * https://www.jsonwebtoken.io/
     * https://jwt.io/
     * @param endpoints
     * @throws Exception
     */
   @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token 默认支持POST方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //JWT生成token配置
        if(jwtAccessTokenConverter!=null && jwtTokenEnhancer!=null){
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 允许使用表单方式进行客户端授权认证---从表单参数获取client_id和client_secret
     * 默认支持从消息头从读取认证信息
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory().withClient("imooc")
                .secret(passwordEncoder.encode("imoocsecret"))
//                .resourceIds(QQ_RESOURCE_ID)
                .accessTokenValiditySeconds(7200) //token有效时间 单位秒 设置两小时
                .authorizedGrantTypes("authorization_code","password","refresh_token"*//*, "implicit"*//*)//设置服务支持的授权模式
                .authorities("ROLE_CLIENT")
                .scopes("all","read","write") //设置客户端访问权限 --客户端可以不写默认使用配置权限 如果带了参数必须是配置的否则出错
                .redirectUris("http://example.com");
//                .autoApprove(true)  //用户授权默认同意设置
//                .autoApprove("get_user_info");*/
        //TODO 修改成配置方式
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
            for(OAuth2ClientProperties config:securityProperties.getOauth2().getClients()){
                builder.withClient(config.getClientId())
                        .secret(passwordEncoder.encode(config.getClientSecret()))
                        .accessTokenValiditySeconds(config.getAccessTokenValidateSeconds()) //token有效时间 单位秒 设置两小时
                        .authorizedGrantTypes("authorization_code","client_credentials","password","refresh_token")//设置服务支持的授权模式
//                        .authorities("ROLE_CLIENT")
                        .scopes("all","read","write") //设置客户端访问权限 --客户端可以不写默认使用配置权限 如果带了参数必须是配置的否则出错
                        .redirectUris("http://example.com");
            }
        }


    }


}
