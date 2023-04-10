package com.pkh.controller;

import com.alibaba.fastjson.JSONObject;
import com.pkh.bean.param.RoleListParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.dao.po.Role;
import com.pkh.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("/list")
    public PikaResponse list(@RequestBody RoleListParam param) {
        log.info("/role/list, param:{}", param);
        try {
            Integer total = roleService.countByParam(param);
            log.info("/role/list count number: {}", total);
            List<Role> roleList = new ArrayList<>();
            if (total > 0) {
                roleList = roleService.getByParam(param);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", roleList);
            return new PikaResponse(jsonObject);
        } catch (Exception e) {
            log.info("/role/list failed, exception: {}", e.getMessage());
            return new PikaResponse("-1","failed!");
        }
    }


}
