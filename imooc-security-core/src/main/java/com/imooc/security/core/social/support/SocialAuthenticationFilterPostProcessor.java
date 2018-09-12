package com.imooc.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 社交认证过滤器后处理器你 --设置过滤器处理的配置
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
