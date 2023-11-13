package com.akhund.moexstockservice.dto;

import com.akhund.moexstockservice.model.Stock;
import com.akhund.stockservice.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StocksDto {
    private List<Stock> stocks;
}
