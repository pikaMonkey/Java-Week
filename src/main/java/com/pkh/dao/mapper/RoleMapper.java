package com.pkh.dao.mapper;

import com.pkh.bean.param.RoleListParam;
import com.pkh.dao.po.Role;

import java.util.List;
import java.util.Map;

/**
 * RoleMapper继承基类
 */
public interface RoleMapper extends MyBatisBaseDao<Role, Long> {

    List<Role> selectByParam(RoleListParam param);

    Integer countByParam(RoleListParam param);

    List<Role> selectByCondition(Map<String, Object> map);

    Integer countByCondition(Map<String, Object> map);
}