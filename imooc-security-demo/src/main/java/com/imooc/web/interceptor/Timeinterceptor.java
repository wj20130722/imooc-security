package com.imooc.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjie on 2018/6/28.
 */
@Component
public class Timeinterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        if(handler instanceof HandlerMethod){
            System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
            System.out.println(((HandlerMethod)handler).getMethod().getName());//controller里面的调用方法
        }
        request.setAttribute("startTime",System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long startTime = (Long)request.getAttribute("startTime");
        System.out.println("time interceptor 耗时："+(System.currentTimeMillis()-startTime));
    }

    //视图解析完成之后调用 一般做资源回收处理或者日志消息记录
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        Long startTime = (Long)request.getAttribute("startTime");
        System.out.println("time interceptor 最终耗时："+(System.currentTimeMillis()-startTime));
        System.out.println("ex is "+ex);
    }
}
