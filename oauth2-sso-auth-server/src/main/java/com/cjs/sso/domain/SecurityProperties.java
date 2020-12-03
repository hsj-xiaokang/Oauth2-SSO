package com.cjs.sso.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author lvhaibao
 * @description
 * @date 2018/11/22 0022 11:54
 */
@Data
@Component
public class SecurityProperties {


    private OauthPageProperties oauthLogin = new OauthPageProperties();

}