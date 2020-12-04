package com.cjs.sso.config;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.alibaba.fastjson.JSON;
import com.cjs.sso.domain.MyUser;
import com.cjs.sso.service.MyUserDetailsService;
import com.cjs.sso.util.CacheConstants;
import com.cjs.sso.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 认证服务配置
 * 
 * @author ruoyi
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    /**
     * 定义授权和令牌端点以及令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    {
        endpoints
                // 请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 指定token存储位置
                .tokenStore(tokenStore())
                // 自定义生成令牌
                .tokenEnhancer(tokenEnhancer)
                // 用户账号密码认证
                .userDetailsService(userDetailsService)
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false);
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
    {
        oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
        //解决Encoded password does not look like BCrypt报错
        //因为springsecurity在最新版本升级后,默认把之前的明文密码方式给去掉了
        //https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
//        oauthServer.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /**
     * 声明 ClientDetails实现
     */
    public RedisClientDetailsService clientDetailsService()
    {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        return clientDetailsService;
    }

    /**
     * 配置客户端详情
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 基于 Redis 实现，令牌保存到缓存
     */
    @Bean
    public TokenStore tokenStore()
    {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
        return tokenStore;
    }

    /**
     * 自定义生成令牌
     */
    @Bean
    public TokenEnhancer tokenEnhancer()
    {
        return (accessToken, authentication) -> {
            if (authentication.getUserAuthentication() != null)
            {
                Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                MyUser user = (MyUser) authentication.getUserAuthentication().getPrincipal();
                //举个例子，假设我们想增加一个字段，这里我们增加一个mobile表示手机号
                //附加的mobile参数
                additionalInformation.put(Constants.MOBILE_ADDITION_NAME, user.getMobile());
                additionalInformation.put(Constants.DETAILS_USER_ID, user.getUserId());
                additionalInformation.put(Constants.DETAILS_USERNAME, user.getUsername());
                log.info("登录成功！tokenEnhancer: {}", JSON.toJSONString(accessToken));
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            }
            return accessToken;
        };
    }
}