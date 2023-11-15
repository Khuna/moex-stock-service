package com.akhund.moexstockservice.service;

import com.akhund.moexstockservice.dto.BondDto;
import com.akhund.moexstockservice.dto.StocksDto;
import com.akhund.moexstockservice.dto.TickersDto;
import com.akhund.moexstockservice.exception.LimitRequestsException;
import com.akhund.moexstockservice.model.Currency;
import com.akhund.moexstockservice.model.Stock;
import com.akhund.moexstockservice.moexclient.CorporateBondsClient;
import com.akhund.moexstockservice.moexclient.GovernmentBondsClient;
import com.akhund.moexstockservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BondService {

    private final CorporateBondsClient corporateBondsClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final Parser parser;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(getCorporateBonds());
        allBonds.addAll(getGovernmentBonds());

        if (allBonds.isEmpty()) {
            throw new LimitRequestsException("Empty dto list getting from Moex.");
        }

        List<BondDto> resultBonds = allBonds.stream()
                .filter(b -> tickersDto.getTickers().contains(b.getTicker()))
                .toList();

        List<Stock> stocks =  resultBonds.stream()
                .map(b -> {
                    return Stock.builder()
                            .ticker(b.getTicker())
                            .name(b.getName())
                            .figi(b.getTicker())
                            .type("bond")
                            .currency(Currency.RUB)
                            .source("MOEX")
                            .build();
                })
                .toList();

        return new StocksDto(stocks);
    }

    public List<BondDto> getCorporateBonds() {
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parser(xmlFromMoex);

        return bondDtos;
     }

    public List<BondDto> getGovernmentBonds() {
        String xmlFromMoex = governmentBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parser(xmlFromMoex);

        return bondDtos;
    }
}
