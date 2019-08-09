package com.qf.sys.shiroconfig;

import com.qf.datas.StatusCode;
import com.qf.datas.SysUserMessage;
import com.qf.sys.pojo.SysPermission;
import com.qf.sys.pojo.SysUser;
import com.qf.sys.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录会触发该方法的调用
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String name = (String)token.getPrincipal();

        SysUserMessage message = sysUserService.getSimpleInof(name);

        /**
         * 走服务降级后，原因在系统本身有问题，并不是用户的密码错了，
         * 所以要提示 “系统错误”, 但是不能直接返回 null, 因为返回空值会
         * 被 IncorrectCredentialsException 或者 UnknownAccountException捕获，
         * 但是错误不在用户。
         */
        if(message.getCode() == StatusCode.serviceDown) {
            throw new AuthenticationException();
        }

        SysUser sysUser = message.getData();

        if(null == sysUser || null == sysUser.getPassword() || "".equals(sysUser.getPassword())) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, sysUser.getPassword(), this.getClass().getName());

        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("======================服务提供方====校验权限===============================");

        String name = (String)principals.getPrimaryPrincipal(); //获取用户名

        SysUser sysUser = sysUserService.getFullInof(name).getData();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<String> permissionList = new ArrayList<>();

        // 获取用户的权限
        Set<SysPermission> permissionSet = sysUser.getPermissionSet();

        // 将用户所拥有的权限，添加集合中
        permissionSet.forEach(sp -> permissionList.add(sp.getPermission()));

        simpleAuthorizationInfo.addStringPermissions(permissionList);

        return simpleAuthorizationInfo;
    }


}
