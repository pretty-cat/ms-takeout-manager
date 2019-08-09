package com.qf.sys.service.impl;


import com.qf.sys.dao.SysUserMapper;
import com.qf.sys.pojo.SysUser;
import com.qf.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;


    @Override
    public SysUser selectSimpleInfo(String name) {
        return userMapper.selectSimpleInfo(name);
    }

    @Override
    public SysUser sysUserPermissionInfo(String name) {
        return userMapper.sysUserPermissionInfo(name);
    }
}
