package com.pkh.service.impl;

import com.pkh.bean.param.UserListParam;
import com.pkh.bean.vo.UserVo;
import com.pkh.dao.mapper.UserMapper;
import com.pkh.dao.po.Rights;
import com.pkh.dao.po.User;
import com.pkh.enums.SexEnum;
import com.pkh.service.UserService;
import javafx.beans.binding.ObjectBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getByParam(UserListParam param) {
        int offset = 0;
        if (!ObjectUtils.isEmpty(param.getPageIndex()) && !ObjectUtils.isEmpty(param.getPageSize())) {
            offset = (param.getPageIndex() - 1) * param.getPageSize();
        }
        param.setOffset(offset);
        List<User> users = userMapper.selectByParam(param);
        // users处理逻辑 kangkang@pika.com
        users.stream().filter(Objects::nonNull).filter(item -> !ObjectUtils.isEmpty(item.getUserId())).forEach(item -> {
            String userId = item.getUserId();
            int index = userId.indexOf('@');
            item.setUserName(userId.substring(0,index));
        });
        return users;
    }

    @Override
    public Integer countByParam(UserListParam param) {
        return userMapper.countByParam(param);
    }

    @Override
    public List<User> getByCondition(UserListParam param) {
        int offset = 0;
        if (!ObjectUtils.isEmpty(param.getPageIndex()) && !ObjectUtils.isEmpty(param.getPageSize())) {
            offset = (param.getPageIndex() - 1) * param.getPageSize();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", param.getUserId());
        map.put("sex", param.getSex());
        return userMapper.selectByCondition(map, offset, param.getPageSize());
    }

    @Override
    public Integer countByCondition(UserListParam param) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", param.getUserId());
        map.put("sex", param.getSex());
        return userMapper.countByCondition(map);
    }

    @Override
    public int add(User param) {
        return userMapper.insertSelective(param);
    }

    @Override
    public User getByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<User> userList = userMapper.selectByCondition(map, null, null);
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public List<Rights> getRights(User user) {
        List<Rights> rightsList;
        int sex = Integer.parseInt(user.getSex());
        if (SexEnum.BOY.getCode() == sex) {
            rightsList = getBoyRights();
        } else if (SexEnum.GIRL.getCode() == sex) {
            rightsList = getGirlRights();
        } else {
            rightsList = getOtherRights();
        }
        return rightsList;
    }



    private List<Rights> getBoyRights() {
        List<Rights> rightsList = new ArrayList<>();
        return rightsList;
    }

    private List<Rights> getGirlRights() {
        List<Rights> rightsList = new ArrayList<>();
        return rightsList;
    }

    private List<Rights> getOtherRights() {
        List<Rights> rightsList = new ArrayList<>();
        return rightsList;
    }


}
