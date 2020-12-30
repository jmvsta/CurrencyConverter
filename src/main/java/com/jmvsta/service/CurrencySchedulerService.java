package com.jmvsta.service;

import com.jmvsta.dto.CurrenciesWrapperDto;
import com.jmvsta.dto.CurrencyConveter;
import com.jmvsta.dto.CurrencyDto;
import com.jmvsta.entity.Currency;
import com.jmvsta.repository.CurrencyRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencySchedulerService {

    private final CurrencyRepository currencyRepository;

    private RestTemplate restTemplate;
    private final String url = "http://www.cbr.ru/scripts/XML_daily.asp";

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(CurrenciesWrapperDto.class, CurrencyDto.class);
        MarshallingHttpMessageConverter marshallingConverter =
                new MarshallingHttpMessageConverter(marshaller);

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(marshallingConverter);
        return converters;
    }

    public List<CurrencyDto> getCurrencies() {

        ResponseEntity<CurrenciesWrapperDto> response =
                restTemplate.getForEntity(url, CurrenciesWrapperDto.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Response status: {}, reason: {}",
                    response.getStatusCode().value(),
                    response.getStatusCode().getReasonPhrase());
            throw new HttpServerErrorException(response.getStatusCode());
        }
        return response.getBody().getValute();
    }

    @Transactional
    public void updateCurrencies(List<CurrencyDto> currencies) {
        for (CurrencyDto currencyDto : currencies) {
            Currency currency = currencyRepository.findByCharCode(currencyDto.getCharCode());
            BigDecimal value = new BigDecimal(currencyDto.getValue().replace(",", "."))
                    .divide(new BigDecimal(currencyDto.getNominal()), 4, RoundingMode.CEILING);
            if (currency == null) {
                currencyRepository.saveAndFlush(CurrencyConveter.toCurrency(currencyDto));
                log.info("added new currency");
            } else if (!currency.getValue().equals(value)) {
                currency.setValue(value);
                currencyRepository.saveAndFlush(currency);
                log.info("updated currency");
            }
        }
    }
}
