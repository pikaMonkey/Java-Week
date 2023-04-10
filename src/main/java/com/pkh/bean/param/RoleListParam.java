package com.pkh.bean.param;

import lombok.Data;

import java.util.List;

@Data
public class RoleListParam extends BaseListParam {
    /**
     * 角色NO
     */
    private String roleNo;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态 0:初始 1:激活 2:失效
     */
    private String status;

    /**
     * 类型
     */
    private String type;

    private List<String> createTime;
}
