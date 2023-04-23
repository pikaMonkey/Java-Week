package com.pkh.commons.datasource;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {
    /**
     * 使用ThreadLocal维护变量
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 管理所有的数据源id
     */
    public static List<String> dataSourceIds = new ArrayList<>();

    /**
     * 设置当前数据源类型
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSource当前是否存在
     *
     * @param dataSourceId  数据源Id
     * @return
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
