package com.qf.sys.service;

import com.qf.sys.pojo.SysUser;
import java.util.List;

public interface SysUserService {

    SysUser selectSimpleInfo(String name);

    SysUser sysUserPermissionInfo(String name);
}
