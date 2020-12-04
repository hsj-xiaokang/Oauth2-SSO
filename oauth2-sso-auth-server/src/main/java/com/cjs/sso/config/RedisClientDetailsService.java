package com.cjs.sso.config;

import javax.sql.DataSource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import com.cjs.sso.util.CacheConstants;
import com.cjs.sso.util.SecurityConstants;


/**
 * 重写原生方法支持redis缓存
 *
 * @author ruoyi
 * @author:heshengjin qq:2356899074
 */
public class RedisClientDetailsService extends JdbcClientDetailsService
{
    public RedisClientDetailsService(DataSource dataSource)
    {
        super(dataSource);
        super.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        super.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
    }

    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId)
    {
        return super.loadClientByClientId(clientId);
    }
}
