package com.akhund.moexstockservice.parser;

import com.akhund.moexstockservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parser(String ratesAsString);
}
