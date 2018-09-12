package com.imooc.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangjie on 2018/6/27.
 */
//@Component
public class TimeFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        System.out.println("time filter start...");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request,response);
        long endTime = System.currentTimeMillis();
        System.out.println("time filter 耗时:"+(endTime-startTime));
        System.out.println("time filter finish...");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destory...");
    }
}
