package cn.smbms.sysfun;

import cn.smbms.pojo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SysInterceptor extends HandlerInterceptorAdapter {
    Log log = LogFactory.getLog(SysInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===================preHandle====================");

        HttpSession session = request.getSession();

        User user =(User) session.getAttribute("userSession");

        if (null==user){
            response.sendRedirect(request.getContextPath()+"/error.jsp");
             return false;
        }
            return true;
    }
}
