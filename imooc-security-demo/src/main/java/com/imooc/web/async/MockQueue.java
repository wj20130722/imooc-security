package com.imooc.web.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by wangjie on 2018/6/29.
 */
@Data
@Slf4j
@Component
public class MockQueue {

    private String placeOrder;//下单消息

    private String completeOrder;//订单完成消息

    public void setPlaceOrder(String placeOrder) throws Exception{
        new Thread(()->{
            log.info("接到下单请求" + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕" + placeOrder);
        }).start();
    }

}
