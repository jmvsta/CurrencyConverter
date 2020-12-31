package com.jmvsta.controller;

import com.jmvsta.dto.ConverterDto;
import com.jmvsta.dto.CurrencyConverter;
import com.jmvsta.entity.Currency;
import com.jmvsta.entity.ExchangeRate;
import com.jmvsta.entity.History;
import com.jmvsta.entity.User;
import com.jmvsta.service.CurrencyService;
import com.jmvsta.service.ExchangeRateService;
import com.jmvsta.service.HistoryService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/converter")
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final CurrencyService currencyService;

    private final ExchangeRateService exchangeRateService;

    private final HistoryService historyService;

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public String convert(@ModelAttribute("converter") ConverterDto filter, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            log.error("no authorized user");
            throw new UsernameNotFoundException("no authorized user");
        }

        List<History> histories = historyService.getAllByUserIdAndFilter(user.getId(), filter);
        List<Currency> currencies = currencyService.getAll();

        model.addAttribute("histories", histories);
        model.addAttribute("currencies", currencies);
        model.addAttribute("converter", filter);

        return "history";
    }

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home(@ModelAttribute("converter") ConverterDto converter, Model model) throws ParseException {
        List<Currency> currencies = currencyService.getAll();
        String sourceCurrId = converter.getSourceCurrId(), targetCurrId = converter.getTargetCurrId();
        BigDecimal val = converter.getSourceValue(), result = null;
        if (sourceCurrId != null && targetCurrId != null && val != null) {
            ExchangeRate sourceExchangeRate = exchangeRateService.getActualByCurrencyId(sourceCurrId);
            ExchangeRate targetExchangeRate = exchangeRateService.getActualByCurrencyId(targetCurrId);
            result = val.multiply(sourceExchangeRate.getValue()).divide(targetExchangeRate.getValue(), 4, RoundingMode.CEILING);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user == null) {
                log.error("no authorized user");
                throw new UsernameNotFoundException("no authorized user");
            }
            historyService.addHistory(
                    CurrencyConverter.toHistory(
                            sourceExchangeRate.getCurrency(),
                            targetExchangeRate.getCurrency(),
                            val,
                            result,
                            sourceExchangeRate.getDate(),
                            user)
            );
        }
        model.addAttribute("converter", converter);
        model.addAttribute("currencies", currencies);
        model.addAttribute("result", result);

        return "converter";
    }


}
