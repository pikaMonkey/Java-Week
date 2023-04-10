package com.pkh.service;

import com.pkh.bean.param.CustomerListParam;
import com.pkh.dao.po.Customer;

import java.util.List;

public interface CustomerService {
    Integer countByParam(CustomerListParam param);
    List<Customer> getByParam(CustomerListParam param);
}
