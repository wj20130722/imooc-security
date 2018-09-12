package com.imooc.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.dto.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangjie on 2018/7/20.
 */
@Slf4j
public class ImoocLogoutSuccessHandler implements LogoutSuccessHandler{

    public ImoocLogoutSuccessHandler(String signOutUrl){
        this.signOutUrl = signOutUrl;
    }
    private String signOutUrl;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("退出成功");
        if(StringUtils.isBlank(signOutUrl)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            //重定向到配置的页面
            redirectStrategy.sendRedirect(request, response, signOutUrl);
        }

    }
}
