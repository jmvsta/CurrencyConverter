package com.jmvsta.service;

import com.jmvsta.dto.CurrencyConverter;
import com.jmvsta.dto.ValuteDto;
import com.jmvsta.entity.Currency;
import com.jmvsta.entity.ExchangeRate;
import com.jmvsta.repository.CurrencyRepository;
import com.jmvsta.repository.ExchangeRateRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeRateService {

    private final CurrencyRepository currencyRepository;

    private final ExchangeRateRepository exchangeRateRepository;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    public ExchangeRate getById(long id) {
        return exchangeRateRepository.findById(id);
    }

    public List<ExchangeRate> getAll() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate getActualByCurrencyId(String valuteId) throws ParseException {
        return exchangeRateRepository.findAllByCurrency_ValuteIdAndDate(valuteId, formatter.parse(formatter.format(new Date())));
    }

    @Transactional
    public void updateExchangeRates(List<ValuteDto> valuteDtos) throws ParseException {
        for (ValuteDto valuteDto : valuteDtos) {
            Currency currency = currencyRepository.findByCharCode(valuteDto.getCharCode());
            ExchangeRate exchangeRate = exchangeRateRepository.findAllByCurrency_ValuteIdAndDate(valuteDto.getId(),
                    formatter.parse(formatter.format(new Date())));
            if (exchangeRate == null) {
                exchangeRateRepository.saveAndFlush(CurrencyConverter.toExchangeRate(valuteDto, currency));
                log.info("updated {} exchange rate", valuteDto.getCharCode());
            } else {
                log.warn("currency's {} exchange rate's been already stored in db", valuteDto.getCharCode());
            }
        }
    }
}
