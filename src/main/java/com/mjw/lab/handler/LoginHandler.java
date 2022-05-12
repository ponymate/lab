package com.mjw.lab.handler;

import com.mjw.lab.dao.mapper.DeviceAdmMapper;
import com.mjw.lab.service.BorrowerService;
import com.mjw.lab.utils.AdmThreadLocal;
import com.mjw.lab.utils.JwtUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class LoginHandler implements HandlerInterceptor {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private DeviceAdmMapper deviceAdmMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
            return true;
        }

        String token = request.getHeader("Authorization");
        if(JwtUtils.checkToken(token)){
            Long id = JwtUtils.getIdByToken(token);
            String type = JwtUtils.getTypeByToken(token);
            if(type.equals("deviceAdm")||type.equals("superAdmin")){
                AdmThreadLocal.set(deviceAdmMapper.selectById(id));
            }
            else UserThreadLocal.set(borrowerService.getBorrowerById(type,id));
        }

        return JwtUtils.checkToken(token);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
        AdmThreadLocal.remove();
    }
}
