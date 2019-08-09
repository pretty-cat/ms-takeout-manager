package com.qf.sys.controller;

import com.qf.datas.Message;
import com.qf.datas.SysUserMessage;
import com.qf.datas.StatusCode;
import com.qf.sys.pojo.SysUser;
import com.qf.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin({"http://localhost"})
public class LoginController {

    @Resource
    private SysUserService sysUserService;


    @PostMapping("/login")
    public Object login(@RequestBody SysUser sysUser) {
        System.out.println("*******************88  8081 登录被调用================");
        /*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getName(), sysUser.getPassword());
        Subject subject = SecurityUtils.getSubject();

        Message message = new Message();

        try {
            subject.login(token);
            message.setCode(StatusCode.success);
            message.setMsg("登录成功");
            String sessionId = (String)subject.getSession().getId();
            message.setData(sessionId);
        }catch (UnknownAccountException ex) {
            message.setCode(StatusCode.fail);
            message.setMsg("用户名或密码错误");
        }catch (IncorrectCredentialsException ex) {  // zhanghuming
            message.setCode(StatusCode.fail);
            message.setMsg("用户名或密码错误");
        }catch (AuthenticationException ex) {
            message.setCode(StatusCode.syserror);
            message.setMsg("系统内部错误");
        }

        return  message;
    }

    @RequestMapping("/no_login")
    public Object noLogin() {
        SysUserMessage message = new SysUserMessage();
        message.setCode(StatusCode.fail);
        message.setMsg("请登录");
        return message;
    }
}
