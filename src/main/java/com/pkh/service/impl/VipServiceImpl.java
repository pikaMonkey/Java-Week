package com.pkh.service.impl;

import com.pkh.dao.po.Vip;
import com.pkh.service.VipService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VipServiceImpl implements VipService {

    public List list() {
        Vip bob = new Vip(1,"bob","microsoft", "US");
        Vip kangkang = new Vip(5,"kangkang","ali", "CN");
        Vip alice = new Vip(4,"alice","apple", "US");
        Vip jane = new Vip(3,"jane","intel", "US-10-04");
        Vip jack = new Vip(2,"jack","ibm", "2004-US-03");
        return Arrays.asList(bob, kangkang, alice, jane, jack);
    }
}
