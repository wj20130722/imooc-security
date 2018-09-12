package com.imooc.security.core.validate.code.template;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器,封装不同校验码的处理逻辑
 * Created by wangjie on 2018/7/10.
 */
public interface ValidateCodeProcessor {
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     * @param request
     * @throws Exception
     */
    void validate(ServletWebRequest request);
}
