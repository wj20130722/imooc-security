package com.imooc.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjie on 2018/6/29.
 */
@Data
@Component
public class DeferredResultHolder {

    private Map<String,DeferredResult<String>> map = new HashMap<>();

}
