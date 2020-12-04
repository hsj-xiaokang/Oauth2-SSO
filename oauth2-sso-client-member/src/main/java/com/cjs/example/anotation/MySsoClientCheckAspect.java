package com.cjs.example.anotation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cjs.example.redis.RedisService;
import com.cjs.example.util.Constants;
import com.cjs.example.util.SecurityUtils;


/**
 * 数据过滤处理
 * 
 * @author:heshengjin qq:2356899074
 */
@Aspect
@Component
public class MySsoClientCheckAspect
{
    @Autowired
    private RedisService redisService;


    // 配置织入点
    @Pointcut("@annotation(com.cjs.example.anotation.MySsoClientCheck)")
    public void MySsoClientCheckPointCut()
    {
    }

    @Before("MySsoClientCheckPointCut()")
    public void doBefore(JoinPoint point) throws Throwable
    {
    	handleMySsoClientCheckPointCut(point);
    }

    protected void handleMySsoClientCheckPointCut(final JoinPoint joinPoint) throws IOException
    {
        // 获得注解
    	MySsoClientCheck controllerMySsoClientCheck = getAnnotationLog(joinPoint);
        if (controllerMySsoClientCheck == null){
            return;
        }
       // 存在注解
       //校验登录用户
       Authentication authentication = SecurityUtils.getAuthentication();
       if(ObjectUtils.isEmpty(authentication)){
          return;
       }
       String userName = authentication.getName();
	   if(StringUtils.isBlank(userName)){
    	   return;
       }
       //校验别的系统是不是登出了
       String uerNameLogout = redisService.getCacheObject(Constants.SSO_CLICENT_LOGOUT_NAME_PREFIX + userName.trim());
       if(StringUtils.isNotBlank(uerNameLogout)){
    	   HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
           HttpServletResponse httpRespon = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
           if(!ObjectUtils.isEmpty(httpRequest) && !ObjectUtils.isEmpty(httpRespon)){
        	   //说明别的系统已经登出
               // 将系统的cookie删掉
               Cookie[] cookies = httpRequest.getCookies();
               if(cookies != null && cookies.length > 0){
                   for (Cookie cookie : cookies){
                       cookie.setMaxAge(0);
                       cookie.setPath("/");
                       httpRespon.addCookie(cookie);
                   }
               }
               //从哪里来，回哪里去
               String referer = httpRequest.getHeader("referer");
               if(StringUtils.isBlank(referer)){
            	   referer = httpRequest.getRequestURI();
               }
               if(referer.contains("?")){
            	   referer += "&t=" + new Date().getTime();
               }
               else{
            	   referer += "?t=" + new Date().getTime();
               }
        	   httpRespon.sendRedirect(referer);
      		   return;
           }
       }
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private MySsoClientCheck getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(MySsoClientCheck.class);
        }
        return null;
    }
}