package com.qf.sys.fallback;

import com.qf.datas.SysUserMessage;
import com.qf.datas.StatusCode;
import com.qf.sys.service.SysUserService;
import org.springframework.stereotype.Component;

@Component
public class SysUserServiceFallback implements SysUserService {

    @Override
    public SysUserMessage getSimpleInof(String name) {
        SysUserMessage message = new SysUserMessage();
        message.setCode(StatusCode.serviceDown);
        message.setMsg("调用失败,服务提供方不可用");
        return message;
    }

    @Override
    public SysUserMessage getFullInof(String name) {
        SysUserMessage message = new SysUserMessage();
        message.setCode(StatusCode.serviceDown);
        message.setMsg("调用失败,服务提供方不可用");
        return message;
    }
}
