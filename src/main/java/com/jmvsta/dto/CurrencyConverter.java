package com.jmvsta.dto;

import com.jmvsta.entity.Currency;
import com.jmvsta.entity.ExchangeRate;
import com.jmvsta.entity.History;
import com.jmvsta.entity.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrencyConverter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    public static Currency toCurrency(ValuteDto valuteDto) {
        return new Currency(valuteDto.getId(),
            Integer.parseInt(valuteDto.getNumCode()),
            valuteDto.getCharCode(),
            valuteDto.getName());
    }

    public static ExchangeRate toExchangeRate(ValuteDto valuteDto, Currency currency) {
        return new ExchangeRate(
                new BigDecimal(valuteDto.getValue().replace(",", "."))
                        .divide(new BigDecimal(valuteDto.getNominal()), 4, RoundingMode.CEILING),
                currency,
                new Date());

    }
    public static History toHistory(Currency curr1,
                                    Currency curr2,
                                    BigDecimal val1,
                                    BigDecimal val2,
                                    Date date,
                                    User user) {
        return new History(curr1, curr2, val1, val2, date, user);

    }

}
