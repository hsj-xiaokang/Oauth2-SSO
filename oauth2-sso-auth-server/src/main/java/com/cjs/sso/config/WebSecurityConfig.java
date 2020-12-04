package com.cjs.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cjs.sso.domain.FromLoginConstant;
import com.cjs.sso.domain.SecurityProperties;
import com.cjs.sso.service.MyUserDetailsService;

/**
 * @author ChengJianSheng
 * @author:heshengjin qq:2356899074
 * @date 2019-02-11
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

 /*   @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**","/oauth/**","/login");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();
         http
         		//表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
         		.formLogin().loginPage(FromLoginConstant.LOGIN_PAGE).loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL)
         		.and()
         	 .authorizeRequests()
             .antMatchers("/oauth/**","/assets/**","**/logout/**" ,
            		 FromLoginConstant.LOGIN_PROCESSING_URL,
                     FromLoginConstant.LOGIN_PAGE,
                     securityProperties.getOauthLogin().getOauthLogin(),
                     securityProperties.getOauthLogin().getOauthGrant()).permitAll()
             .and().authorizeRequests().anyRequest().authenticated();
         
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
