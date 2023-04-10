package com.pkh.bean.param;

import com.pkh.annotation.Replace;
import lombok.Data;

import java.util.List;

@Data
public class UserListParam extends BaseListParam{

    @Replace(source = "_", target = " ")
    private String userId;

    private String sex;

    private List<String> createTime;

    public UserListParam() {
    }

    public UserListParam(String userId) {
        this.userId = userId;
    }
}
