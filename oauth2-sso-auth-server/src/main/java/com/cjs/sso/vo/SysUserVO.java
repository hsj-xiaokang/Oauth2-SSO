package com.cjs.sso.vo;

import com.cjs.sso.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 * @author:heshengjin qq:2356899074
 */
@Data
public class SysUserVO extends SysUser {

    /**
     * 权限列表
     */
    private List<String> authorityList;

}
