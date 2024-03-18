package com.pkh.controller;

import com.pkh.bean.param.UserListParam;
import com.pkh.bean.param.UserParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.dao.po.User;
import com.pkh.service.LoginService;
import com.pkh.service.UserService;
import com.pkh.util.RedisUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    UserService userService;

    @Resource
    LoginService loginService;

    @Resource
    RedisUtil redisUtil;

    @RequestMapping("/identity/login")
    public PikaResponse<Object> login (@RequestBody UserParam userParam) {
        if (ObjectUtils.isEmpty(userParam.getUserId()) || ObjectUtils.isEmpty(userParam.getPassword())) {
            return new PikaResponse<>("-1", "账号或密码为空");
        }

        User user = userService.getByUserId(userParam.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return new PikaResponse<>("-1","账号不存在！");
        }

        String token = loginService.login(userParam.getUserId(), userParam.getPassword());
        if (StringUtils.hasLength(token)) {
            redisUtil.set(token, user.getUserId(), 10000L);
            return new PikaResponse<>(token);
        }
        return new PikaResponse<>("-1", "登录失败，系统异常");
    }

    private String generateToken(UserListParam userListParam) {
        return "token";
    }

    private boolean validateUser(User user) {
        // 模拟用户验证逻辑
        // 此处省略实际的用户验证过程，假设用户名和密码均正确
        return true;
    }
}
