package com.pkh.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 角色表
 */
public class Role implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色NO
     */
    private String roleNo;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态 0:初始 1:激活 2:失效
     */
    private String status;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}