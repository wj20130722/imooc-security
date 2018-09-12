package com.imooc.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 短信验证码对应的用户鉴权信息
 * Created by wangjie on 2018/7/11.
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken{
    private static final long serialVersionUID = 500L;
    private final Object principal;

    //登录成功之前存放手机号码
    public SmsCodeAuthenticationToken(String mobile) {
        super((Collection)null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    //登录成功之后存放身份信息
    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
