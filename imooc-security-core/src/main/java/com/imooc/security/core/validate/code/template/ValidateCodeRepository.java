package com.imooc.security.core.validate.code.template;

import com.imooc.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by wangjie on 2018/7/22.
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code,ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param code
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);


}
