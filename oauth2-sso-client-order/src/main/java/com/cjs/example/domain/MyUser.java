package com.cjs.example.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 大部分时候直接用User即可不必扩展
 * @author ChengJianSheng
 * @date 2019-02-11
 */
@Data
public class MyUser extends User {

    private String mobile;  //  举个例子，假设我们想增加一个字段，这里我们增加一个mobile表示手机号

    /**
     * 用户ID
     */
    private Integer userId;
    
    public MyUser(String username, String password,String mobile,Integer userId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mobile = mobile;
        this.userId = userId;
    }

    public MyUser(String username, String password,String mobile,Integer userId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.mobile = mobile;
        this.userId = userId;
    }
    
    
}
