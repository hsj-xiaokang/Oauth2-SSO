package com.cjs.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 
 *  说明：main
 *  @author:heshengjin qq:2356899074
 *  @date 2020年12月4日 下午2:52:52
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class Oauth2SsoClientOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2SsoClientOrderApplication.class, args);
    }

}
