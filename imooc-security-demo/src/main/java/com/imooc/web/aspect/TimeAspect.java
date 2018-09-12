package com.imooc.web.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by wangjie on 2018/6/28.
 */
@Component
@Aspect
public class TimeAspect {

    @Around("execution(* com.imooc.web.controller.UserController.*(..))")
    public Object handleContollerMethod(ProceedingJoinPoint pjp){
        System.out.println("time aspect start...");
        long startTime = System.currentTimeMillis();
        Object result = null;
        System.out.println(Arrays.toString(pjp.getArgs()));
        try {
            result = pjp.proceed(pjp.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time aspect 耗时:"+(endTime-startTime));
        System.out.println("time aspect end...");
        return result;
    }



}
