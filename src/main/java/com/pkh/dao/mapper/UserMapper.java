package com.pkh.dao.mapper;

import com.pkh.bean.param.UserListParam;
import com.pkh.dao.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * UserMapper继承基类
 */
public interface UserMapper extends MyBatisBaseDao<User, Long> {

    List<User> selectByParam(UserListParam param);

    List<User> selectByCondition(@Param("map") Map<String, Object> map, @Param("offset")Integer offset, @Param("pageSize") Integer pageSize);

    Integer countByCondition(@Param("map")Map<String, Object> map);

    Integer countByParam(UserListParam param);

}