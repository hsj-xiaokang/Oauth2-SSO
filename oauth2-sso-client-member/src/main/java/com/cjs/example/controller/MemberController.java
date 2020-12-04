package com.cjs.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cjs.example.anotation.MySsoClientCheck;
import com.cjs.example.domain.MyUser;
import com.cjs.example.redis.RedisService;
import com.cjs.example.util.Constants;
import com.cjs.example.util.SecurityUtils;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

/**
 * @author ChengJianSheng
 * @author:heshengjin qq:2356899074
 * @date 2019-03-03
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
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
    @MySsoClientCheck
    @GetMapping("/tagLogoutUserName2Redis")
    @ResponseBody
    public Principal tagLogoutUserName2Redis(Principal principal) {
    	String logoutUserName = ( !ObjectUtils.isEmpty(principal) && !ObjectUtils.isEmpty(principal.getName()) ) ? principal.getName().trim() : "";
    	redisService.setCacheObject(Constants.SSO_CLICENT_LOGOUT_NAME_PREFIX + logoutUserName, logoutUserName);
        return principal;
    }
	
	/**
	 * 
	 * @Title: list   
	 * @Description: TODO(登录成功主页课件member列表)   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
    @MySsoClientCheck
    @GetMapping("/list")
    public String list() {
        return "member/list";
    }

    @MySsoClientCheck
    @GetMapping("/iframeMember")
    public String iframeMember() {
        return "member/iframeMember";
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
    	MyUser myUser = SecurityUtils.getLoginUser();
    	log.info(JSON.toJSONString(myUser));
        return myUser;
    }
    @MySsoClientCheck
    @GetMapping("/securityContext")
    @ResponseBody
    public SecurityContext securityContext() {
    	SecurityContext securityContext = SecurityContextHolder.getContext();
    	log.info(JSON.toJSONString(securityContext));
        return securityContext;
    }
    @PreAuthorize("hasAuthority('member:save')")
    @ResponseBody
    @PostMapping("/add")
    @MySsoClientCheck
    public String add() {

        return "add";
    }

    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @GetMapping("/detail")
    @MySsoClientCheck
    public String detail() {
        return "detail";
    }
}
