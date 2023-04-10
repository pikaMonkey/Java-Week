package com.pkh.service.impl;

import com.pkh.bean.param.RoleListParam;
import com.pkh.dao.mapper.RoleMapper;
import com.pkh.dao.po.Role;
import com.pkh.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> getByParam(RoleListParam param) {
        Integer offset = 0;
        if (!ObjectUtils.isEmpty(param.getPageIndex()) && !ObjectUtils.isEmpty(param.getPageSize())) {
            offset = (param.getPageIndex() - 1) * param.getPageSize();
        }
        param.setOffset(offset);
        return roleMapper.selectByParam(param);
    }

    @Override
    public Integer countByParam(RoleListParam param) {
        return roleMapper.countByParam(param);
    }

    @Override
    public List<Role> getByCondition(RoleListParam param) {
        Map<String, Object> map = new HashMap<>();
        map.put("role_no", param.getRoleNo());
        map.put("name", param.getName());
        map.put("status", param.getStatus());
        map.put("type", param.getType());
        return roleMapper.selectByCondition(map);
    }

    @Override
    public Integer countByCondition(RoleListParam param) {
        Map<String, Object> map = new HashMap<>();
        map.put("role_no", param.getRoleNo());
        map.put("name", param.getName());
        map.put("status", param.getStatus());
        map.put("type", param.getType());
        return roleMapper.countByCondition(map);
    }

    @Override
    public int add(Role role) {
        return roleMapper.insertSelective(role);
    }
}
