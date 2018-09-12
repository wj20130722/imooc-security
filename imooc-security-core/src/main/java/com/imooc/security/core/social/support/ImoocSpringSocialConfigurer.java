package com.imooc.security.core.social.support;

import lombok.Data;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * spring-social过滤器相关配置 包含处理的url 请求处理跳转的url
 * Created by wangjie on 2018/7/15.
 */
@Data
public class ImoocSpringSocialConfigurer extends SpringSocialConfigurer {
    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public ImoocSpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl); //社交过滤器处理的请求
        //filter.setConnectionAddedRedirectUrl("/"); //连接添加之后
        // 判断是否存在社交认证后处理器（即判断是浏览器端还是app端）
        if(socialAuthenticationFilterPostProcessor!=null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T)filter;
    }
}
