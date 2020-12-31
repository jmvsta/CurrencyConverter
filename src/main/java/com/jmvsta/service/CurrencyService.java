package com.jmvsta.service;

import com.jmvsta.dto.CurrencyConverter;
import com.jmvsta.dto.ValuteDto;
import com.jmvsta.entity.Currency;
import com.jmvsta.repository.CurrencyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Currency getById(String id) {
        return currencyRepository.findCurrencyByValuteId(id);
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    @Transactional
    public void updateCurrencies(List<ValuteDto> valuteDtos) {
        for (ValuteDto valuteDto : valuteDtos) {
            Currency currency = currencyRepository.findByCharCode(valuteDto.getCharCode());
            if (currency == null) {
                currencyRepository.saveAndFlush(CurrencyConverter.toCurrency(valuteDto));
                log.info("added new currency {}", valuteDto.getCharCode());
            }
        }
    }
}
