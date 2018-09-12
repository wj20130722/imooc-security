package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Created by wangjie on 2018/7/8.
 */
@Data
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
