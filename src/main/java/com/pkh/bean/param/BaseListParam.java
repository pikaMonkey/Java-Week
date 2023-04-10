package com.pkh.bean.param;

import lombok.Data;

import java.util.List;

@Data
public class BaseListParam {
    // 页码
    private Integer pageIndex;

    // 页面大小
    private Integer pageSize;

    // 排序字段
    private String sortBy;

    // 顺序: 正序 倒序
    private String sortOrder;

    // 分页计算offset
    private Integer offset;

    // 语言类型
    private List langTypes;
}
