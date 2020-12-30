package com.jmvsta.dto;

import com.jmvsta.entity.Currency;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConveter {

    public static Currency toCurrency(CurrencyDto currencyDto) {
        return new Currency(currencyDto.getId(),
            Integer.parseInt(currencyDto.getNumCode()),
            currencyDto.getCharCode(),
            currencyDto.getName(),
                new BigDecimal(currencyDto.getValue().replace(",", "."))
                        .divide(new BigDecimal(currencyDto.getNominal()), 4, RoundingMode.CEILING));

    }
}
