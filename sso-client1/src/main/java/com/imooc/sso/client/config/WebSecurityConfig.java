package com.imooc.sso.client.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by wangjie on 2018/7/21.
 */
@Configuration
@EnableOAuth2Sso
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login",
                        "/error",
                        "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .csrf().disable()
        ;
    }


}
