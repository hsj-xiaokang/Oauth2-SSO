package com.cjs.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author ChengJianSheng
 * @date 2019-03-03
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //本地退出，告知认证中心退出
    	  http.logout()
          .logoutSuccessUrl("http://myauth.com:8080/logout");
         
	 	  	 http
		     .authorizeRequests()
		     .antMatchers(
		         "/iframeMember","/bootstrap/**","/oauth/**").permitAll()
		     .anyRequest().authenticated()
		     .and().csrf().disable();
	 	  // 允许跨域访问
	      http.cors();
          http.headers().frameOptions().disable();
    }
}

