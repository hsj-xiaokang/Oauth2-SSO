package com.cjs.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;


/**
 * @author ChengJianSheng
 * @date 2019-03-03
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private RemoteTokenServices remoteTokenServices;
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

    /**
     * 
     *  说明：增强解析token更多信息
     *  @author:heshengjin qq:2356899074
     *  @date 2020年12月4日 下午2:08:55
     */
	@Override
	public void init(WebSecurity web) throws Exception {
		  super.init(web);
	      DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
	      UserAuthenticationConverter userTokenConverter = new CommonUserConverter();
	      accessTokenConverter.setUserTokenConverter(userTokenConverter);
	      remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
	}
}

