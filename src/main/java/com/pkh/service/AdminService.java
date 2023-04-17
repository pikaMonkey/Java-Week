package com.pkh.service;

import com.pkh.bean.param.AdminListParam;
import com.pkh.dao.po.admindb.AdminUser;

import java.util.List;

public interface AdminService {
    Integer countByParam(AdminListParam param);
    List<AdminUser> getByParam(AdminListParam param);
}
