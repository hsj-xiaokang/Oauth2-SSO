package com.cjs.example.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 某个客户端退出时候，会把退出的userName存入Redis，登录时候删除Redis
 * 这样Redis存在userName说明某个系统退出了，那么清理cookies重定向回认证中心
 * @ClassName:  mySsoClientCheck   
 * @Description:TODO(客户端校验单点登录还在不在，不在的话退出并重定向到认证中心)   
 * @author: heshengjin qq:2356899074
 * @date:   2020年9月6日 上午11:39:41   
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  MySsoClientCheck {

}
