package com.qf.sys.shiroconfig;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class CustomerSessionManager extends DefaultWebSessionManager {

    // 从请求的 Header中获取 名为 token的值。
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader("token");

        System.out.println("sessionId = " + sessionId);

        if(null != sessionId) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);

            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            // always set rewrite flag - SHIRO-361
            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());

            return sessionId;
        }else {
            return super.getSessionId(request, response);
        }
    }
}
