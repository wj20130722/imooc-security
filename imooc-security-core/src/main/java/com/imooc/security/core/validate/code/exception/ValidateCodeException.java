package com.imooc.security.core.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by wangjie on 2018/7/8.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
