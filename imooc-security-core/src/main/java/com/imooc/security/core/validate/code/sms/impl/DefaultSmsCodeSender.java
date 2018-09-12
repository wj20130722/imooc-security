package com.imooc.security.core.validate.code.sms.impl;

import com.imooc.security.core.validate.code.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wangjie on 2018/7/9.
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender{
    @Override
    public void send(String mobile, String code) {
        log.info("向手机号:{}发送短信验证码:{}",mobile,code);
    }
}
