package com.cjs.example.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cjs.example.domain.MyUser;
import com.cjs.example.util.Constants;


/**
 * https://my.oschina.net/giegie/blog/3023768 根据checktoken 的结果转化用户信息
 * @author lengleng
 */
public class CommonUserConverter implements UserAuthenticationConverter {
    private static final String N_A = "N/A";

    /**
     * 将授权信息返回到资源服务
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Map<String, Object> authMap = new LinkedHashMap<>();
        authMap.put(USERNAME, userAuthentication.getName());
        if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty()) {
            authMap.put(AUTHORITIES, AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities()));
        }
        return authMap;
    }

    /**
     * 获取用户认证信息
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

            Integer userId = (Integer)(map.get(Constants.DETAILS_USER_ID));
            String username = (String) map.get(Constants.DETAILS_USERNAME);
            String mobile = (String) map.get(Constants.DETAILS_USERNAME);
            MyUser myUser = new MyUser(username, N_A,mobile,userId, true, true, true, true, authorities);
            System.out.println("==========CommonUserConverter========");
            System.out.println(JSON.toJSONString(myUser));
            return new UsernamePasswordAuthenticationToken(myUser, N_A, authorities);
        }
        return null;
    }

    /**
     * 获取权限资源信息
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
