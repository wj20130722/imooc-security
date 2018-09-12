package com.imooc.security.core.authentication.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信验证码认证处理(校验短信验证码放在filter统一处理，此处只返回认证信息)---匹配对应的token进行相应处理
 * Created by wangjie on 2018/7/11.
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

    @Getter
    @Setter
    private UserDetailsService userDetailsService;

    /**
     * <strong>通过userdetailservice获取用户信息,认证成功，重新组装认证成功的Authentication</strong>
     * @param authentication AuthenticationManager传过来的Authentication---未认证的token
     * @return 认证成功的Authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
        if(null==user){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
