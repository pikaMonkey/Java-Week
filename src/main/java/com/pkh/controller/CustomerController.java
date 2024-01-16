package com.pkh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pkh.bean.param.CustomerListParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.dao.po.Customer;
import com.pkh.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/customer")
@Slf4j
@RestController
public class CustomerController {

    @Resource
    CustomerService customerService;

    @PostMapping("/list")
    public PikaResponse list(CustomerListParam param) {
        log.info("/customer/list, list param: {}", param);
        try {
            Integer total = customerService.countByParam(param);
            log.info("/customer/list, count number: {}", total);
            List<Customer> customerList = customerService.getByParam(param);
            if (total > 0) {
                customerList = customerService.getByParam(param);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", customerList);
            return new PikaResponse(jsonObject);
        } catch (Exception e) {
            log.info("/customer/list, failed! exception:{} ", e.getMessage());
            return new PikaResponse("1", e.getMessage());
        }
    }
}
