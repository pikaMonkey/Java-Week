package com.pkh.service.impl;

import com.pkh.bean.param.UserListParam;
import com.pkh.dao.mapper.UserMapper;
import com.pkh.dao.po.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl();
        userServiceImplUnderTest.userMapper = mock(UserMapper.class);
    }

    @Test
    void testGetByParam() {
        // 构造userMapper.selectByParam()的参数
        final UserListParam param = new UserListParam();
        param.setUserId("kangkang@pika.com");

        // 构造userMapper.selectByParam()返回值
        final User user = new User();
        user.setUserId("kangkang@pika.com");
        final List<User> users = Arrays.asList(user);
        when(userServiceImplUnderTest.userMapper.selectByParam(param)).thenReturn(users);

        // 执行userService.getByParam()
        final List<User> result = userServiceImplUnderTest.getByParam(param);

        // 验证结果
        result.forEach(item -> {
            System.out.println(item.toString() + "\n");
        });
    }

    @Test
    void testGetByParam_UserMapperReturnsNoItems() {
        // Setup
        final UserListParam param = new UserListParam();
        param.setUserId("kangkang@pika.com");

        when(userServiceImplUnderTest.userMapper.selectByParam(new UserListParam()))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceImplUnderTest.getByParam(param);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCountByParam() {
        // Setup
        final UserListParam param = new UserListParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        param.setOffset(0);
        param.setUserId("userId");
        param.setSex("sex");
        param.setCreateTime(Arrays.asList("value"));

        when(userServiceImplUnderTest.userMapper.countByParam(new UserListParam())).thenReturn(0);

        // Run the test
        final Integer result = userServiceImplUnderTest.countByParam(param);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetByCondition() {
        // Setup
        final UserListParam param = new UserListParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        param.setOffset(0);
        param.setUserId("userId");
        param.setSex("sex");
        param.setCreateTime(Arrays.asList("value"));

        // Configure UserMapper.selectByCondition(...).
        final User user = new User();
        user.setId(0L);
        user.setUserId("userId");
        user.setUserName("userName");
        user.setPassword("password");
        user.setDealPassword("dealPassword");
        user.setSex("sex");
        user.setType("type");
        user.setPhone("phone");
        user.setMobie("mobie");
        user.setEmail("email");
        user.setWeChat("weChat");
        user.setTelegram("telegram");
        user.setAddress("address");
        user.setZipCode("zipCode");
        user.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<User> users = Arrays.asList(user);
        when(userServiceImplUnderTest.userMapper.selectByCondition(new HashMap<>(), 0, 0)).thenReturn(users);

        // Run the test
        final List<User> result = userServiceImplUnderTest.getByCondition(param);

        // Verify the results
    }

    @Test
    void testGetByCondition_UserMapperReturnsNoItems() {
        // Setup
        final UserListParam param = new UserListParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        param.setOffset(0);
        param.setUserId("userId");
        param.setSex("sex");
        param.setCreateTime(Arrays.asList("value"));

        when(userServiceImplUnderTest.userMapper.selectByCondition(new HashMap<>(), 0, 0))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceImplUnderTest.getByCondition(param);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCountByCondition() {
        // Setup
        final UserListParam param = new UserListParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        param.setOffset(0);
        param.setUserId("userId");
        param.setSex("sex");
        param.setCreateTime(Arrays.asList("value"));

        when(userServiceImplUnderTest.userMapper.countByCondition(new HashMap<>())).thenReturn(0);

        // Run the test
        final Integer result = userServiceImplUnderTest.countByCondition(param);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testAdd() {
        // Setup
        final User param = new User();
        param.setId(0L);
        param.setUserId("userId");
        param.setUserName("userName");
        param.setPassword("password");
        param.setDealPassword("dealPassword");
        param.setSex("sex");
        param.setType("type");
        param.setPhone("phone");
        param.setMobie("mobie");
        param.setEmail("email");
        param.setWeChat("weChat");
        param.setTelegram("telegram");
        param.setAddress("address");
        param.setZipCode("zipCode");
        param.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(userServiceImplUnderTest.userMapper.insertSelective(any(User.class))).thenReturn(0);

        // Run the test
        final int result = userServiceImplUnderTest.add(param);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
