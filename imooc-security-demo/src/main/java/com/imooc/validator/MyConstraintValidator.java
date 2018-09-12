package com.imooc.validator;

import com.imooc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by wangjie on 2018/6/26.
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object>{

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("my constraint validator init...");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println(helloService.greeting("tom"));
        System.out.println(value);
        return false;
    }
}
