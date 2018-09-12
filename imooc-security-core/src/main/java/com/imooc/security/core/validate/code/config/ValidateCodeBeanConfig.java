package com.imooc.security.core.validate.code.config;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.template.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.image.ImageCodeGenerator;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;
import com.imooc.security.core.validate.code.sms.impl.DefaultSmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangjie on 2018/7/8.
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
