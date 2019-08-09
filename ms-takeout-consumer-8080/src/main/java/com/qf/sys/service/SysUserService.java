package com.qf.sys.service;

import com.qf.datas.SysUserMessage;
import com.qf.sys.fallback.SysUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * rpc(remote procedure call), http请求也可以叫 rpc
 * 通常再说rpc的时候，通过高效的序列化方式，进行通信。
 *
 * EJB(不能跨语言，使用Java语言写的)
 * webservice技术(框架CXF), 是基于soap协议(http+xml) 银行项目
 * http: 也就是现在用的这种方式(基于Restful风格的请求)。
 * dubbo、netty(mina), grpc(google), thrift(facebook)
 */
@FeignClient(name="takeout-provider", fallback = SysUserServiceFallback.class)
public interface SysUserService {

    @RequestMapping("/sys_user/simple_info")
    public SysUserMessage getSimpleInof(@RequestParam String name);

    @RequestMapping("/sys_user/full_info")
    public SysUserMessage getFullInof(@RequestParam String name);
}
