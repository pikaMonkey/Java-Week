package com.pkh.bean.param;

import com.pkh.annotation.Replace;
import lombok.Data;

import java.util.List;

@Data
public class UserParam extends BaseListParam{

    private String userId;

    private String password;

    public UserParam(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

