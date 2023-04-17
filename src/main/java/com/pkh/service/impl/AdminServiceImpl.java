package com.pkh.service.impl;

import com.pkh.bean.param.AdminListParam;
import com.pkh.dao.mapper.admindb.AdminUserMapper;
import com.pkh.dao.po.admindb.AdminUser;
import com.pkh.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminUserMapper adminUserMapper;

    @Override
    public Integer countByParam(AdminListParam param) {
        Map<String, Object> map = new HashMap<>();
        return adminUserMapper.countByCondition(map);
    }

    @Override
    public List<AdminUser> getByParam(AdminListParam param) {
        int offset = 0;
        if (!ObjectUtils.isEmpty(param.getPageIndex()) && !ObjectUtils.isEmpty(param.getPageSize())) {
            offset = (param.getPageIndex() - 1) * param.getPageSize();
        }
        Map<String, Object> map = new HashMap<>();
        return adminUserMapper.selectByCondition(map, offset, param.getPageSize());
    }
}
