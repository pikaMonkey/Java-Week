package com.pkh.configuraton;

import com.pkh.bean.param.BaseListParam;
import com.pkh.dao.prop.PikaDayBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class PikaDayConfigurationTest extends BaseListParam {

    @Resource
    PikaConfiguration pikaDayConfiguration;

    @Value("${pikaday.startTime}")
    private long startTime;

    @Value("${pikaday.endTime}")
    private long endTime;

    @Test
    void testGetPikaDayBean() {
        PikaDayBean pikaDayBean = pikaDayConfiguration.getPikaDayBean();
        Assertions.assertNotNull("pikaDayBean", "pikaDay配置类获取为空");
        log.info(pikaDayBean.toString());
    }

    @Test
    void testGetPikaDayValue() {
        log.info("startTime: {}, endTime:{}", startTime, endTime);
    }
}