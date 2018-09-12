package com.imooc.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/7/17.
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp{


    @Override
    public String execute(Connection<?> connection) {
        //TODO 根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }
}