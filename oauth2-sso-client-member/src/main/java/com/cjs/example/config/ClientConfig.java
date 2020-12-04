package com.cjs.example.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * 
 *  说明：配置restemplate
 *  @author:heshengjin qq:2356899074
 *  @date 2020年12月4日 下午2:48:52
 */
@Configuration
public class ClientConfig {
 
  // 注入拦截器。拦截器也可以不声明为Bean, 直接在这里新建实例
  @Autowired
  ActionTrackInterceptor actionTrackInterceptor;
 
  // 声明为Bean，方便应用内使用同一实例
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    // 把自定义的ClientHttpRequestInterceptor添加到RestTemplate，可添加多个
    restTemplate.setInterceptors(Collections.singletonList(actionTrackInterceptor));
    return restTemplate;
  }
}