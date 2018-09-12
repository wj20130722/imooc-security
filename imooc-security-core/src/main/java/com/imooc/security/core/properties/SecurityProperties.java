package com.imooc.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wangjie on 2018/7/5.
 */
@Data
@ConfigurationProperties("imooc.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

}
