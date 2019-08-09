package com.qf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyZuulFilter extends ZuulFilter {

    @Value("${zull.filter.enable}")
    private Boolean isEnable;


    /**
     * pre
     * post
     * routing
     * error
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤的顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    // 是否过滤
    @Override
    public boolean shouldFilter() {
        return isEnable;
    }

    //过滤逻辑
    @Override
    public Object run() throws ZuulException {
        System.out.println("过滤方法执行===========================================");
        return null;
    }
}
