package com.akhund.moexstockservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Stock {

    private String ticker;
    private String figi;
    private String name;
    private String type;
    private Currency currency;
    private String source;
}
