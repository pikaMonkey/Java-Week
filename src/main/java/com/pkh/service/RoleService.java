package com.pkh.service;

import com.pkh.bean.param.RoleListParam;
import com.pkh.dao.po.Role;

import java.util.List;

public interface RoleService {

    List<Role> getByParam(RoleListParam param);

    Integer countByParam(RoleListParam param);

    List<Role> getByCondition(RoleListParam param);

    Integer countByCondition(RoleListParam param);

    int add(Role role);
}
