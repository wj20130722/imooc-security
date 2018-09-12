package com.imooc.security.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by wangjie on 2018/7/19.
 */
//@EnableRedisHttpSession
public class SpringSessionConfig {

    /*@Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }*/

}
