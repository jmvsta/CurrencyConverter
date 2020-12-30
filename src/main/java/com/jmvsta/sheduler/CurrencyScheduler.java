package com.jmvsta.sheduler;

import com.jmvsta.dto.CurrencyDto;
import com.jmvsta.service.CurrencySchedulerService;
import java.text.SimpleDateFormat;
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

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Scheduled(fixedRate = 60000)
    public void renewCurrencies() {
        List<CurrencyDto> currencyDtos = currencySchedulerService.getCurrencies();
        currencySchedulerService.updateCurrencies(currencyDtos);
    }
}
