package com.imooc.web.async;

import lombok.extern.java.Log;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * Created by wangjie on 2018/6/29.
 */
@Log
@RestController
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @GetMapping("/order")
    public DeferredResult<String> order() throws Exception{
        log.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber,result);





        /*Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("副线程开始");
                Thread.sleep(1000);
                log.info("副线程返回");
                return "处理完成";
            }
        };*/
        log.info("主线程返回");
        return result;
    }



}
