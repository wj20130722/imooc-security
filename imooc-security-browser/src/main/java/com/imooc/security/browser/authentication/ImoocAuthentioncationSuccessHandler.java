package com.imooc.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.config.SecurityCoreConfig;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangjie on 2018/7/5.
 */
@Slf4j
@Component("imoocAuthentioncationSuccessHandler")
public class ImoocAuthentioncationSuccessHandler /*implements AuthenticationSuccessHandler*/ extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if(LoginType.JSON==securityProperties.getBrowser().getLoginType()){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            // 如果设置了earthchen.security.browser.singInSuccessUrl，总是跳到设置的地址上
            // 如果没设置，则尝试跳转到登录之前访问的地址上，如果登录前访问地址为空，则跳到网站根路径上
            if (StringUtils.isNotBlank(securityProperties.getBrowser().getSingInSuccessUrl())) {
                requestCache.removeRequest(request, response);//清除保存的地址
                setAlwaysUseDefaultTargetUrl(true); //使用默认配置的跳转地址
                setDefaultTargetUrl(securityProperties.getBrowser().getSingInSuccessUrl());
            }
            //重定向到需要认证的请求页面
            super.onAuthenticationSuccess(request,response,authentication);
        }


    }
}
