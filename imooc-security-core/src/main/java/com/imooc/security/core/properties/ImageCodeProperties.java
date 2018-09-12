package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Created by wangjie on 2018/7/8.
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
    public ImageCodeProperties(){
        setLength(4);
    }
    private int width = 67;
    private int height = 23;

}
