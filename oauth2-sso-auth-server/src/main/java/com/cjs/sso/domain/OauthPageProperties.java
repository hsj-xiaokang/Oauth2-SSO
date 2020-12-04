package com.cjs.sso.domain;

import lombok.Data;

/**
 * html页面
 * @author lvhaibao
 * @description
 * @date 2018/12/26 0026 18:27
 * @author:heshengjin qq:2356899074
 */
@Data
public class OauthPageProperties {

    private  String  oauthLogin = "/login";

    private  String oauthGrant = "/grant";
}
