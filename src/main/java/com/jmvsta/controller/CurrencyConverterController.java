package com.jmvsta.controller;

import com.jmvsta.dto.ConverterDto;
import com.jmvsta.entity.Currency;
import com.jmvsta.entity.History;
import com.jmvsta.service.CurrencyService;
import com.jmvsta.service.HistoryService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    private final HistoryService historyService;

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public String convert(@ModelAttribute("converter") ConverterDto converter, Model model) {
        List<History> histories = historyService.getAll();
        List<Currency> currencies = currencyService.getAll();

        model.addAttribute("histories", histories);
        model.addAttribute("currencies", currencies);
        model.addAttribute("converter", converter);



        return "history";
    }

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home(@ModelAttribute("converter") ConverterDto converter, Model model) {
        List<Currency> currencies = currencyService.getAll();
        Long idA = converter.getCurrAId(), idB = converter.getCurrBId();
        BigDecimal val = converter.getValA(), result = null;
        if (idA != null && idB != null && val != null) {
            Currency curA = currencyService.getById(idA);
            Currency curB = currencyService.getById(idB);
            result = val.multiply(curA.getValue()).divide(curB.getValue(), 4, RoundingMode.CEILING);
            historyService.createHistory(curA, curB, val, result);
        }
        model.addAttribute("converter", converter);
        model.addAttribute("currencies", currencies);
        model.addAttribute("result", result);
//        Currency currency = currencyService.getById(id);

        return "converter";
    }


}
