package com.akhund.moexstockservice.moexclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "governmentbonds", url = "${moex.bonds.government.url}")
public interface GovernmentBondsClient {

    @GetMapping
    String getBondsFromMoex();
}
