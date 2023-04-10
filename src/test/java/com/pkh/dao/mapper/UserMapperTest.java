package com.pkh.dao.mapper;

import com.pkh.bean.param.UserListParam;
import com.pkh.dao.po.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void selectByParam() {
        UserListParam param = new UserListParam();
        param.setUserId("kangkang@pika.com");
        List<User> users = userMapper.selectByParam(param);
        Assertions.assertNotNull(users);
    }
}