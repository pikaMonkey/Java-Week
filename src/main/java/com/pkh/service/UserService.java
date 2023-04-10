package com.pkh.service;

import com.pkh.bean.param.UserListParam;
import com.pkh.dao.po.Rights;
import com.pkh.dao.po.User;

import java.util.List;

public interface UserService {

    List<User> getByParam(UserListParam param);

    Integer countByParam(UserListParam param);

    List<User> getByCondition(UserListParam param);

    Integer countByCondition(UserListParam param);

    int add(User param);

    User getByUserId(String userId);

    List<Rights> getRights(User user);
}
