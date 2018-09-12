package com.imooc.security.app;


import javax.servlet.http.HttpServletRequest;

import com.imooc.security.app.social.AppSingUpUtils;
import com.imooc.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class AppSecurityController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSingUpUtils appSingUpUtils;

    /**
     * APP端社交登录需要注册时跳到这里，返回401和用户信息给前端 引导APP端用户去注册认证
     *
     * @param request
     * @return
     */
    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils
                .getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickName(connection.getDisplayName());
        userInfo.setHeadImg(connection.getImageUrl());
//        保存到redis
        appSingUpUtils.saveConnectionData(new ServletWebRequest(request),
                connection.createData());
        return userInfo;
    }


}

