package com.jmvsta.service;

import com.jmvsta.dto.CurrenciesWrapperDto;
import com.jmvsta.dto.ValuteDto;
import com.jmvsta.repository.CurrencyRepository;
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
        marshaller.setClassesToBeBound(CurrenciesWrapperDto.class, ValuteDto.class);
        MarshallingHttpMessageConverter marshallingConverter =
                new MarshallingHttpMessageConverter(marshaller);

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(marshallingConverter);
        return converters;
    }

    @Transactional
    public List<ValuteDto> getValutes() {
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


}
