package com.pkh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Slf4j
@SpringBootTest
class HttpUtilTest {

    @Value("${oa.baseUrl}")
    private String baseUrl;

    static HttpUtil httpUtil;
    static {
        httpUtil = new HttpUtil();
    }

    @Test
    void testDoPost() {
        JSONObject param = new JSONObject();
        String res = HttpUtil.doPost(baseUrl + "/user/list", param, "utf8");
        Object parse = JSON.parse(res);
        log.info(res);
    }

    @Test
    void testDoGet() throws UnsupportedEncodingException {
        String res = HttpUtil.doGet(baseUrl + "/user/list", new HashMap<>(), new HashMap<>());
        Object parse = JSON.parse(res);
        log.info(res);
    }

    @Test
    void testGetHttpClient() {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        System.out.println(httpClient.toString());
    }
}
