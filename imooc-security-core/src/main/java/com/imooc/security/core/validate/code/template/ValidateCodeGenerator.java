package com.imooc.security.core.validate.code.template;

import com.imooc.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器接口包含图形验证码以及短信验证码
 * Created by wangjie on 2018/7/8.
 */
public interface ValidateCodeGenerator {
    public ValidateCode generate(ServletWebRequest request);
}
