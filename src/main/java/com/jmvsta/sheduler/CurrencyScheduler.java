package com.jmvsta.sheduler;

import com.jmvsta.dto.ValuteDto;
import com.jmvsta.service.CurrencySchedulerService;
import com.jmvsta.service.CurrencyService;
import com.jmvsta.service.ExchangeRateService;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "currency.scheduler.enable")
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final CurrencySchedulerService currencySchedulerService;

    private final CurrencyService currencyService;

    private final ExchangeRateService exchangeRateService;

//    starts at 00:00 everyday
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 1_440_000)
    public void renewCurrencies() throws ParseException {
        List<ValuteDto> valuteDtos = currencySchedulerService.getValutes();
        currencyService.updateCurrencies(valuteDtos);
        exchangeRateService.updateExchangeRates(valuteDtos);
    }
}
