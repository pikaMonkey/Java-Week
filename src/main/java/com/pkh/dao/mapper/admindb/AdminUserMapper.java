package com.pkh.dao.mapper.admindb;

import com.pkh.dao.po.admindb.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    Integer countByCondition(@Param("map")Map<String, Object> map);

    List<AdminUser> selectByCondition(@Param("map") Map<String, Object> map, @Param("offset")Integer offset, @Param("pageSize") Integer pageSize);
}