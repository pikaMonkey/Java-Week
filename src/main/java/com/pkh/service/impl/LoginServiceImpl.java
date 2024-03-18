package com.pkh.service.impl;

import com.pkh.service.LoginService;
import com.pkh.util.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public String login(String username, String password) {
        String token = JWTUtil.sign(username, password);
        return token;
    }
}
