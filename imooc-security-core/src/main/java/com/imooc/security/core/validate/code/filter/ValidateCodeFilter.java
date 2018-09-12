package com.imooc.security.core.validate.code.filter;

import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ImageCode;
import com.imooc.security.core.validate.code.controller.ValidateCodeController;
import com.imooc.security.core.validate.code.exception.ValidateCodeException;
import com.imooc.security.core.validate.code.template.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.template.ValidateCodeProcessorHolder;
import com.imooc.security.core.validate.code.template.ValidateCodeType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 验证码过滤器---包含图形验证码以及短信验证码验证
 * Created by wangjie on 2018/7/7.
 */
@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.SMS);
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param codeType
     */
    private void addUrlToMap(String urlString, ValidateCodeType codeType) {
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString,",");
            for (String url:urls){
                urlMap.put(url,codeType);
            }
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType codeType = getValidateCodeType(request);
        if(null!=codeType){
            log.info("校验请求({})中的验证码，验证码类型{}",request.getRequestURI(),codeType);
            try{
                validateCodeProcessorHolder.findValidateCodeProcessor(codeType)
                        .validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urls = urlMap.keySet();
            for (String url:urls){
                if(pathMatcher.match(url,request.getRequestURI())){
                    result = urlMap.get(url);
                    break;
                }
            }
        }
        return result;
    }
}
