package com.pkh.learn.pibatis;

import com.pkh.dao.po.User;

import java.util.List;

public interface UserMapper {

    @Select("select * from t_user where userId = #{userId} and userName = #{userName}")
    List<User> getUser(String userId, String userName);

    User getUserById(Long id);
}
