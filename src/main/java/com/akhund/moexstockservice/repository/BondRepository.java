package com.akhund.moexstockservice.repository;

import com.akhund.moexstockservice.dto.BondDto;
import com.akhund.moexstockservice.dto.FigiesDto;
import com.akhund.moexstockservice.dto.StockPriceDto;
import com.akhund.moexstockservice.exception.LimitRequestsException;
import com.akhund.moexstockservice.moexclient.CorporateBondsClient;
import com.akhund.moexstockservice.moexclient.GovernmentBondsClient;
import com.akhund.moexstockservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BondRepository {

    private final CorporateBondsClient corporateBondsClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final Parser parser;

    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds() {

        log.info("Getting corporate bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parser(xmlFromMoex);

        if (bondDtos.isEmpty()) {
            throw new LimitRequestsException("Empty dto list getting from Moex.");
        }

        return bondDtos;
    }

    @Cacheable(value = "govs")
    public List<BondDto> getGovernmentBonds() {

        log.info("Getting government bonds from Moex");
        String xmlFromMoex = governmentBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parser(xmlFromMoex);

        if (bondDtos.isEmpty()) {
            throw new LimitRequestsException("Empty dto list getting from Moex.");
        }

        return bondDtos;
    }

}
