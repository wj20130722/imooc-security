package com.imooc.security.core.properties;


import lombok.Data;

/**
 * oauth2配置
 */
@Data
public class OAuth2Properties {

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

    /**
     * jwt的签名
     */
    private String jwtSigningKey = "imooc";



}
