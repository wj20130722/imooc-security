package com.imooc.security.core.validate.code.sms;

/**
 * 短信发送接口
 * Created by wangjie on 2018/7/8.
 */
public interface SmsCodeSender {
    void send(String mobile,String code);
}
