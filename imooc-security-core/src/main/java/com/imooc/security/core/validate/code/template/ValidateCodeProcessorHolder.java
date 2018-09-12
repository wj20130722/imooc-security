package com.imooc.security.core.validate.code.template;

import com.imooc.security.core.validate.code.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wangjie on 2018/7/10.
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
