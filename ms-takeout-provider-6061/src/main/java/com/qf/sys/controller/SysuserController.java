package com.qf.sys.controller;

import com.qf.datas.SysUserMessage;
import com.qf.datas.StatusCode;
import com.qf.sys.pojo.SysUser;
import com.qf.sys.service.SysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/sys_user")
public class SysuserController {

    @Resource
    private SysUserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/simple_info")
    public SysUserMessage getSimpleInof(String name) {
        System.out.println("============调用 服务提供方 getSimpleInof =================" + name);
        SysUserMessage message = new SysUserMessage();

        SysUser sysUser = userService.selectSimpleInfo(name);

        message.setData(sysUser);
        message.setCode(StatusCode.success);
        message.setMsg("success");
        return message;
    }

    @RequestMapping("/full_info")
    public SysUserMessage getFullInof(String name) {
        System.out.println("============调用 服务提供方 getFullInof=================");
        SysUserMessage message = new SysUserMessage();

        SysUser sysUser = userService.sysUserPermissionInfo(name);

        message.setData(sysUser);
        message.setCode(StatusCode.success);
        message.setMsg("success");
        return message;
    }
}
