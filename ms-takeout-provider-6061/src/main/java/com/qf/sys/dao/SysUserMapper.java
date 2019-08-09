package com.qf.sys.dao;

import com.qf.sys.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends MyMapper<SysUser> {
    //根据用户名获取用户的信息, 简单信息
    SysUser selectSimpleInfo(String name);

    SysUser sysUserPermissionInfo(String name);
}