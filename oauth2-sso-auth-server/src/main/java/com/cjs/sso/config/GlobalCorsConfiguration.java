package com.cjs.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 
 *  说明：跨域
 *  @author:heshengjin qq:2356899074
 *  @date 2020年12月4日 下午2:45:09
 */
@Configuration
public class GlobalCorsConfiguration   implements WebMvcConfigurer {
    //添加到容器中管理
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
          config.addAllowedOrigin("*");
          config.addAllowedMethod("*");
          config.addAllowedHeader("*");
        //???
//        config.addExposedHeader("*");
        config.setMaxAge(3600L);
        config.setAllowCredentials(true);
 
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
 
        return new CorsFilter(configSource);
    }
}