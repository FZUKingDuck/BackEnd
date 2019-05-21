package com.forum.demo.Aspect;


import com.forum.demo.ResponseResult.JsonResult;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class LoginAspect {

    private final Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    @Autowired
    RedisOperator redisOperator;

    @Pointcut("@annotation(com.forum.demo.Annotation.MonitorRequest)")
    public void loginPointCut(){

    }

    //登录检测
    @Around(value = "loginPointCut()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("is in it");
        //获取request的attribute
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取用户的session
        HttpSession session = request.getSession();

        String username = (String)session.getAttribute("loginName");

        String key = "loginUser:"+username;
        System.out.println(username);
        String value = redisOperator.get(key);

        if(value==null){
            return JsonResult.fail();
        }
        else {
            return joinPoint.proceed();
        }
    }

    @Pointcut("@annotation(com.forum.demo.Annotation.LogPointCut)")
    public void logPointCut(){}


    //日志拦截
    @Before(value = "logPointCut()")
    public void logInfo(JoinPoint joinPoint) throws Throwable{

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("url:"+request.getRequestURI());
        logger.info("ip:"+request.getRemoteHost());
        logger.info("method:"+request.getMethod());
        logger.info("class_method:"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("arg:"+joinPoint.getArgs());

    }


}
