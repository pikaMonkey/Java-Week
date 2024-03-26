package com.pkh.controller;

import com.pkh.bean.param.OrderCreateParam;
import com.pkh.bean.response.PikaResponse;
import com.pkh.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Resource
    StockService stockService;

    @PostMapping("/createOrder")
    public PikaResponse<Object> createOrder(@RequestBody OrderCreateParam param) {
        Boolean deductResult = stockService.deductStock(param.getProductId(), param.getCount());
        if (deductResult) {
            // 库存扣减成功，后续操作（如创建订单等）
            // ...
        }
        return new PikaResponse<>("下单成功");
    }

    @PostMapping("/createOrderSafely")
    public PikaResponse<Object> createOrderSafely(@RequestBody OrderCreateParam param) {
        Boolean haveStock = stockService.deductStockWithRedisson(param.getProductId(), param.getCount());
        if (haveStock) {
            // 创建订单逻辑...
            return new PikaResponse<>("下单成功");
        } else {
            // 库存不足，返回错误信息
            return new PikaResponse<>("库存扣减失败");
        }
    }
}
