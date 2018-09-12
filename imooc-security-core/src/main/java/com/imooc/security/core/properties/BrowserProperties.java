package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Created by wangjie on 2018/7/5.
 */
@Data
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String signOutUrl;

    //登录成功跳转的链接地址
    private String singInSuccessUrl;

    //社交登录处理中根据openid获取对应的业务系统的userid为空的时候需要跳转的注册页面
    private String signUpUrl = "/imooc-signup.html";
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    private LoginType loginType = LoginType.JSON;
    private int remeberMeSeconds = 3600;
}
