package com.akhund.moexstockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class StockPrice {
    private String figi;
    private Double price;
}
