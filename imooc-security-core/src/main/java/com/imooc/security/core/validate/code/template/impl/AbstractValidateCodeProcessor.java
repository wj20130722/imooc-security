package com.imooc.security.core.validate.code.template.impl;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.exception.ValidateCodeException;
import com.imooc.security.core.validate.code.template.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.template.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.template.ValidateCodeRepository;
import com.imooc.security.core.validate.code.template.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 校验码生成逻辑的抽象实现类
 * Created by wangjie on 2018/7/10.
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{

    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    /**
     * 根据不同的处理类生成对应的校验码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 发送校验码 交由子类具体实现
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    //将生成的校验码存放到session中
    private void save(ServletWebRequest request, C validateCode) {
        //图片验证码

        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());

        validateCodeRepository.save(request,code,getValidateCodeType(request));
        //sessionStrategy.setAttribute(request, getSessionKey(request), code);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }



    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        //根据处理子类获取校验码类型
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        //根据请求类型获取校验码类型 --校验逻辑处理的时候就出问题
//        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType codeType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);
        //C codeInSession = (C)sessionStrategy.getAttribute(request,sessionKey);
        C codeInSession = (C)validateCodeRepository.get(request,codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(codeType + "获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            //sessionStrategy.removeAttribute(request, sessionKey);
            validateCodeRepository.remove(request,codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }
        //sessionStrategy.removeAttribute(request, sessionKey);
        validateCodeRepository.remove(request,codeType);
    }
}
