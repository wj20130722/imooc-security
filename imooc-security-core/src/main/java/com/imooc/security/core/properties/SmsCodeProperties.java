package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Created by wangjie on 2018/7/8.
 */
@Data
public class SmsCodeProperties {
    private int length = 6;
    private int expireIn = 300;
    private String url = "";

}
