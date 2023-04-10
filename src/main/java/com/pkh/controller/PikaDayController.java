package com.pkh.controller;

import com.pkh.bean.param.PikaDayEditParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.configuraton.PikaConfiguration;
import com.pkh.dao.prop.PikaDayBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/pikaDay")
public class PikaDayController {

    @Resource
    PikaConfiguration pikaDayConfiguration;

    /**
     * 报名
     *
     * @param param
     * @return
     */
    @PostMapping("/create")
    public PikaResponse<Object> create (@RequestBody PikaDayEditParam param) {
        PikaDayBean pikaDayBean = pikaDayConfiguration.getPikaDayBean();
        long startTime = pikaDayBean.getStartTime();
        long endTime = pikaDayBean.getEndTime();
        long currentTime = System.currentTimeMillis();
        if (currentTime < startTime || currentTime > endTime) {
            return new PikaResponse<>("1", "不在活动时间！");
        }
        // 执行具体的报名逻辑
        return new PikaResponse<>();
    }
}
