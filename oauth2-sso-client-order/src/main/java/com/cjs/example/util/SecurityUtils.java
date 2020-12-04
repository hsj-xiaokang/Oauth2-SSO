package com.cjs.example.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cjs.example.domain.MyUser;




/**
 * 权限获取工具类
 * 
 * @author ruoyi
 * @author:heshengjin qq:2356899074
 */
public class SecurityUtils
{
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public static String getUsername()
    {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户
     */
    public static MyUser getLoginUser(Authentication authentication)
    {
        Object principal = authentication.getPrincipal();
        if (principal instanceof MyUser)
        {
            return (MyUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public static MyUser getLoginUser()
    {
        Authentication authentication = getAuthentication();
        if (authentication == null)
        {
            return null;
        }
        return getLoginUser(authentication);
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
