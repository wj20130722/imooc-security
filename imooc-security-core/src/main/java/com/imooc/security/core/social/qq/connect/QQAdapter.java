package com.imooc.security.core.social.qq.connect;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * api获取的原数据和spring-social提供的标准的Connection数据的适配
 * Created by wangjie on 2018/7/14.
 */
public class QQAdapter implements ApiAdapter<QQ>{

    //测试当前api是否可用
    @Override
    public boolean test(QQ api) {
        return true;
    }

    //将提供数据服务的原数据转化成Connection所需的数据
    @Override
    public void setConnectionValues(QQ api, ConnectionValues connectionValues) {
        QQUserInfo userInfo = api.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());//设置需要显示的用户名
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());//设置用户头像
        connectionValues.setProfileUrl(null);//设置用户主页url
        connectionValues.setProviderUserId(userInfo.getOpenId());//服务商的用户ID
    }

    //获取用户主页信息
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    //更新用户主页状态
    @Override
    public void updateStatus(QQ qq, String s) {
        //do nothing else...
    }
}
