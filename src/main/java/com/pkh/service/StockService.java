package com.pkh.service;

public interface StockService {
    Boolean deductStock(String productId, int count);

    Boolean deductStockWithRedisson(String productId, int count);
}
