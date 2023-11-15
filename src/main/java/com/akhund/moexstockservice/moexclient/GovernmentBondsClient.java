package com.akhund.moexstockservice.moexclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "governmentbonds", url = "${moex.bonds.government.url}", configuration = FeignConfig.class)
public interface GovernmentBondsClient {

    @GetMapping
    String getBondsFromMoex();
}
