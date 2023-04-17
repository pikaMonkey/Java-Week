package com.pkh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pkh.annotation.PkhLog;
import com.pkh.bean.param.UserListParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.bean.vo.UserVo;
import com.pkh.dao.po.Rights;
import com.pkh.dao.po.User;
import com.pkh.enums.SexEnum;
import com.pkh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/list")
    @PkhLog
    public PikaResponse<Object> list(@RequestBody UserListParam param) throws JsonProcessingException {
        Integer count = userService.countByParam(param);
        log.info("/user/list count number: {}", count);
        List<User> userList = new ArrayList<>();
        if (count > 0) {
            userList = userService.getByParam(param);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", userList);
        return new PikaResponse<>(jsonObject);
    }

    @PostMapping("/getByCondition")
    public PikaResponse<Object> getByCondition(@RequestBody UserListParam param) {
        log.info("/user/getByCondition param: {}", param);
        try {
            Integer count = userService.countByCondition(param);
            log.info("/user/getByCondition count number: {}", count);
            List<User> userList = new ArrayList<>();
            if (count > 0) {
                userList = userService.getByCondition(param);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", count);
            jsonObject.put("list", userList);
            return new PikaResponse<>(jsonObject);
        } catch (Exception e) {
            log.info("/user/getByCondition failed, exception:{}", e.getMessage());
            return new PikaResponse<>("-1", "failed!");
        }
    }

    @PostMapping("/create")
    public PikaResponse create(@RequestBody User param) {
        log.info("/user/create, param:{}", param);
        try {
            Integer count = this.countByUserId(param);
            if (count <= 0) {
                return new PikaResponse("-1","Current user already exist!" + param.getUserId());
            }
            int res = userService.add(param);
            log.info("create new user, res: {}", res);
            if (res < 0) {
                return new PikaResponse("-1", "failed！");
            }
            return new PikaResponse<>(res);
        } catch (Exception e) {
            log.info("/user/create, res:{}",e.getMessage());
            return new PikaResponse("-1", "failed!");
        }
    }

    /**
     * 根据用户Id统计个数
     *
     * @param param 参数
     * @return
     */
    private Integer countByUserId(User param) {
        UserListParam userListParam = new UserListParam();
        userListParam.setUserId(param.getUserId());
        return userService.countByCondition(userListParam);
    }

    @PostMapping("/permissions")
    public PikaResponse<JSONObject> permissions() {
        String userId = "kangkang@pika.com";
        User user = userService.getByUserId(userId);
        if (ObjectUtils.isEmpty(user)) {
            return new PikaResponse<>("1", userId + " not exit");
        }
        List<Rights> rights = userService.getRights(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rights", rights);
        return new PikaResponse<>(jsonObject);
    }

    /**
     * 格式化性别
     *
     * @param user 用户
     * @return
     */
    private UserVo formatSex(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        String sexStr = "";
        SexEnum sexEnum = SexEnum.getByCode(Integer.valueOf(user.getSex()));
        if (!ObjectUtils.isEmpty(sexEnum)) {
            sexStr = sexEnum.getDescription();
        }
        userVo.setSex(sexStr);
        return userVo;
    }



}
