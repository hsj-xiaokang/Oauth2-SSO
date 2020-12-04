package com.cjs.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjs.example.anotation.MySsoClientCheck;
import com.cjs.example.domain.MyUser;
import com.cjs.example.redis.RedisService;
import com.cjs.example.util.Constants;
import com.cjs.example.util.SecurityUtils;

/**
 * @author ChengJianSheng
 * @date 2019-03-03
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RedisService redisService;
	/**
	 * 
	 * @Title: tagLogoutUserName2Redis   
	 * @Description: TODO(调用这个之后，才调用退出自己工程里面的logout，才调用认证中心的logout)   
	 * @param: @param principal
	 * @param: @return      
	 * @return: Principal      
	 * @throws
	 */
    @GetMapping("/tagLogoutUserName2Redis")
    @ResponseBody
    @MySsoClientCheck
    public Principal tagLogoutUserName2Redis(Principal principal) {
    	String logoutUserName = ( !ObjectUtils.isEmpty(principal) && !ObjectUtils.isEmpty(principal.getName()) ) ? principal.getName().trim() : "";
    	redisService.setCacheObject(Constants.SSO_CLICENT_LOGOUT_NAME_PREFIX + logoutUserName, logoutUserName);
        return principal;
    }
	/**
	 * 
	 * @Title: list   
	 * @Description: TODO(登录可见list)   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
    @MySsoClientCheck
    @GetMapping("/list")
    public String list() {
        return "order/list";
    }
    
    @MySsoClientCheck
    @GetMapping("/iframeOrder")
    public String iframeOrder() {
        return "order/iframeOrder";
    }

    /**
     * 
     * @Title: info   
     * @Description: TODO(用户信息)   
     * @param: @param principal
     * @param: @return      
     * @return: Principal      
     * @throws
     */
    @MySsoClientCheck
    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }
    @MySsoClientCheck
    @GetMapping("/myUserInfo")
    @ResponseBody
    public MyUser myUserInfo() {
        return SecurityUtils.getLoginUser();
    }
}
