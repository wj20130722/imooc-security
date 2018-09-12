package com.imooc.config;

import com.imooc.web.filter.TimeFilter;
import com.imooc.web.interceptor.Timeinterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjie on 2018/6/28.
 */
//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private Timeinterceptor timeinterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        //registry.addInterceptor(timeinterceptor).addPathPatterns("/**");
    }

    //springmvc 异步支持
    /*@Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
    }*/

    //@Bean
    public FilterRegistrationBean timerFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        List<String> urls = new ArrayList<String>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}
