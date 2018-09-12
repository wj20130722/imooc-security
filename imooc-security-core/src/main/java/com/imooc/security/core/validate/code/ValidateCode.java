package com.imooc.security.core.validate.code;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by wangjie on 2018/7/7.
 */
@Data
public class ValidateCode implements Serializable{

    private static final long serialVersionUID = 8535649232967249737L;
    
    protected String code;

    protected LocalDateTime expireTime;

    public ValidateCode(){

    }


    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }






}
