package com.pkh.dao.po;

public class Vip {
    // ID
    private long id;

    // 用户ID
    private String userId;

    // 公司
    private String company;

    // 国家编码
    private String countryCode;

    public Vip(long id, String userId, String company, String countryCode) {
        this.id = id;
        this.userId = userId;
        this.company = company;
        this.countryCode = countryCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
