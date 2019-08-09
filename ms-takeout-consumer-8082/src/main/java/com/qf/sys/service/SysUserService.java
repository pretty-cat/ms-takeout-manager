package com.qf.sys.service;

import com.qf.datas.SysUserMessage;
import com.qf.sys.fallback.SysUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="takeout-provider", fallback = SysUserServiceFallback.class)
public interface SysUserService {

    @RequestMapping("/sys_user/simple_info")
    public SysUserMessage getSimpleInof(@RequestParam String name);

    @RequestMapping("/sys_user/full_info")
    public SysUserMessage getFullInof(@RequestParam String name);
}
