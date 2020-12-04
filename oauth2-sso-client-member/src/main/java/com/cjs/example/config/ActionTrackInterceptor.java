package com.cjs.example.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 
 *  说明：拦截restemplate
 *  @author:heshengjin qq:2356899074
 *  @date 2020年12月4日 下午2:48:29
 */
@Component
public class ActionTrackInterceptor implements ClientHttpRequestInterceptor {

	 /**
     * 令牌类型
     */
    public static final String BEARER_TOKEN_TYPE = "Bearer";

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)throws IOException {
		 HttpHeaders headers = request.getHeaders();
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 Authentication authentication = securityContext.getAuthentication();
	     if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails){
	    	 OAuth2AuthenticationDetails dateils = (OAuth2AuthenticationDetails) authentication.getDetails();
	    	 // 加入自定义字段
			 headers.add(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, dateils.getTokenValue()));
	     }
		 // 保证请求继续被执行
		 return execution.execute(request, body);
	}
}