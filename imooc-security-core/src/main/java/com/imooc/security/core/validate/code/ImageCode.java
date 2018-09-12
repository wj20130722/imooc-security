package com.imooc.security.core.validate.code;

import lombok.*;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by wangjie on 2018/7/7.
 */

@EqualsAndHashCode(callSuper = true)
public class ImageCode extends ValidateCode /*implements Serializable*/{

//    private static final long serialVersionUID = -2603272744165529541L;
    @Getter
    @Setter
    private BufferedImage image;

    public ImageCode(){

    }


    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, int expireIn){
        super(code,expireIn);
        this.image = image;
    }






}
