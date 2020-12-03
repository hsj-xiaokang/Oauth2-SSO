package com.cjs.sso.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cjs.sso.domain.FromLoginConstant;
import com.cjs.sso.domain.SecurityProperties;

/**
 * 自定义登录页面login
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class LoginController {

	    @Autowired
	    private SecurityProperties securityProperties;
	/**
     * 当用户没登录的时候，会经过这个请求，在这个请求中可以处理一些逻辑
     *
     * @param request  request
     * @param response response
     * @return ResultModel
     * @throws IOException IOException
     */
	@RequestMapping(FromLoginConstant.LOGIN_PAGE)
    public String requireAuthentication() {
    	return securityProperties.getOauthLogin().getOauthLogin();
    }


    /**
     * 自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
     *
     * @param model   model
     * @param request request
     * @return String
     * @throws Exception Exception
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
       
        ModelAndView view = new ModelAndView();
        view.setViewName(securityProperties.getOauthLogin().getOauthGrant());
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        return view;
    }

    /**
     * 获取信息
     *  @author:heshengjin qq:2356899074
        @date 2020年12月3日 下午3:02:17
     */
    @GetMapping("/infoDetail")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }
    /**
     * 
     * @Title: index   
     * @Description: TODO(登录成功的主页)   
     * @param: @return      
     * @return: String      
     * @throws
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
