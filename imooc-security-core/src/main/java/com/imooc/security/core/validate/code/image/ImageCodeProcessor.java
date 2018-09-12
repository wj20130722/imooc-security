package com.imooc.security.core.validate.code.image;

import com.imooc.security.core.validate.code.ImageCode;
import com.imooc.security.core.validate.code.template.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 *
 * @author wangjie
 *
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception{
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
