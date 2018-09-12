package com.imooc.web.async;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/6/29.
 */
@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        new Thread(()->{
            while (true){
                if(StringUtils.isNotBlank(mockQueue.getCompleteOrder())){
                    String orderNumber = mockQueue.getCompleteOrder();
                    log.info("返回订单处理结果：{}",orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                }else{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
