package com.pkh.controller;

import com.alibaba.fastjson.JSONObject;
import com.pkh.bean.response.PikaResponse;
import com.pkh.service.VipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vip")
public class VipController {

    @Resource
    VipService vipService;

    @RequestMapping("/list")
    public PikaResponse list() {
        try {
            List vipList = vipService.list();
            Integer total = vipList.size();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list",vipList);
            PikaResponse pikaResponse = new PikaResponse<>(jsonObject);
            return pikaResponse;
        } catch (Exception e) {
            log.info("/vip/list exception: {}",e.getMessage());
        }
        return new PikaResponse("-1", "get list failed!");
    }
}
