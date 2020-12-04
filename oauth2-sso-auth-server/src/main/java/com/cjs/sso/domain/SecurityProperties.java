package com.cjs.sso.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author lvhaibao
 * @description
 * @date 2018/11/22 0022 11:54
 * @author:heshengjin qq:2356899074
 */
@Data
@Component
public class SecurityProperties {


    private OauthPageProperties oauthLogin = new OauthPageProperties();

}