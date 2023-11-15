package com.akhund.moexstockservice.service;

import com.akhund.moexstockservice.dto.*;
import com.akhund.moexstockservice.exception.BondNotFoundException;
import com.akhund.moexstockservice.exception.LimitRequestsException;
import com.akhund.moexstockservice.model.Currency;
import com.akhund.moexstockservice.model.Stock;
import com.akhund.moexstockservice.moexclient.CorporateBondsClient;
import com.akhund.moexstockservice.moexclient.GovernmentBondsClient;
import com.akhund.moexstockservice.parser.Parser;
import com.akhund.moexstockservice.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BondService {

    private final BondRepository bondRepository;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        log.info("Request fot tickers {}", tickersDto.getTickers());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovernmentBonds());

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


    public StockPriceDto getPricesByFigies(FigiesDto figiesDto) {
        log.info("Request prices for figies {}", figiesDto.getFigies());
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovernmentBonds());
        figies.removeAll((allBonds.stream()
                .map(b -> b.getTicker()).toList()));

        if (!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }

        List<StockPrice> stockPrices = allBonds.stream()
                .filter(b -> figiesDto.getFigies().contains(b.getTicker()))
                .map(b -> new StockPrice(b.getTicker(), b.getPrice() * 10))
                .toList();

        return new StockPriceDto(stockPrices);
    }
}
