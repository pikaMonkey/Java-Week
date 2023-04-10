package com.pkh.enums;

import java.util.Objects;

/**
 * 性别枚举
 */
public enum SexEnum {
    BOY(0, "男"),
    GIRL(1, "女"),
    OTHERS(2, "保密");

    /**
     * 性别编码
     */
    private Integer code;

    /**
     * 性别描述
     */
    private String description;

    /**
     * 构造函数
     *
     * @param code 性别编码
     * @param description 性别描述
     */
    SexEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取性别枚举
     *
     * @param code  性别编码
     * @return
     */
    public static SexEnum getByCode(Integer code) {
        for (SexEnum e : SexEnum.values()) {
            if (Objects.equals(code, e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
