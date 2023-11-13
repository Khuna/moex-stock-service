package com.akhund.moexstockservice.controller;

import com.akhund.moexstockservice.dto.StocksDto;
import com.akhund.moexstockservice.dto.TickersDto;
import com.akhund.moexstockservice.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {

    private final BondService bondService;

    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {
        return bondService.getBondsFromMoex(tickersDto);
    }
}
