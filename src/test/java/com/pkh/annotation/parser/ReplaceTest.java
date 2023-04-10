package com.pkh.annotation.parser;

import com.pkh.bean.param.BaseListParam;
import com.pkh.bean.param.UserListParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReplaceTest extends BaseListParam {

    @Test
    void parse() {
        UserListParam param = new UserListParam(" kangkang@pika.com ");
        Object parseObject = new ReplaceParser().parse(param);
        if (parseObject instanceof UserListParam) {
            UserListParam parseParam = (UserListParam) parseObject;
            Assertions.assertNotNull(parseParam);
        }
    }
}