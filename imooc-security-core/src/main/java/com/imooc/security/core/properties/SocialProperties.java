package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Created by wangjie on 2018/7/15.
 */
@Data
public class SocialProperties {
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();
}
