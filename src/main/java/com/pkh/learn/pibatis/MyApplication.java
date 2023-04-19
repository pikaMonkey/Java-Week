package com.pkh.learn.pibatis;

import com.pkh.dao.po.User;

import java.util.List;

public class MyApplication {
    public static void main(String[] args) {
        UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUser("kangkang@pika.com", "kangkang");

        UserMapper mapper = new MapperImpl();
        List<User> userList1 = mapper.getUser("kangkang@pika.com", "kangkang");


    }
}
