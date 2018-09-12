package com.imooc.code;

import com.imooc.security.core.validate.code.ImageCode;
import com.imooc.security.core.validate.code.template.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by wangjie on 2018/7/8.
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator{
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("应用级别定义的高级版的图形验证码生成代码...");
        return null;
    }
}
