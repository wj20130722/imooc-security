package com.imooc.security.core.validate.code.filter;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ImageCode;
import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.exception.ValidateCodeException;
import com.imooc.security.core.validate.code.template.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.template.ValidateCodeType;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 短信验证码校验过滤器---->针对手机号登陆进行拦截
 * Created by wangjie on 2018/7/7.
 */
@Data
public class SmsCodeFilterBAK extends OncePerRequestFilter implements InitializingBean{

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Set<String> urls = new HashSet<String>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
        for (String configUrl:configUrls){
            urls.add(configUrl);
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        StringUtils.equals("/authentication/form",request.getRequestURI())
//                && StringUtils.endsWithIgnoreCase(request.getMethod(),"post")
        //判断请求的url和配置的url是否匹配
        boolean action = false;
        for (String url : urls){
            if(pathMatcher.match(url,request.getRequestURI())){
                action = true;
                break;
            }
        }
        if(action){
            try{
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e){
                //失败处理
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }

        }
        filterChain.doFilter(request,response);

    }

    private void validate(ServletWebRequest request) {

        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+ ValidateCodeType.SMS);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    "smsCode");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+ ValidateCodeType.SMS);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+ ValidateCodeType.SMS);

    }
}
