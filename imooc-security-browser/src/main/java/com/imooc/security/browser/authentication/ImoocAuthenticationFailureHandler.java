package com.imooc.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.dto.SimpleResponse;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangjie on 2018/7/5.
 */
@Slf4j
@Component("imoocAuthenticationFailureHandler")
public class ImoocAuthenticationFailureHandler /*implements AuthenticationFailureHandler*/ extends SimpleUrlAuthenticationFailureHandler{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        log.info("登录失败");
        if(LoginType.JSON==securityProperties.getBrowser().getLoginType()){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            //response.getWriter().write(objectMapper.writeValueAsString(exception));
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request,response,exception);
        }

    }
}
