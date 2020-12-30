package com.jmvsta.service;

import com.jmvsta.entity.Currency;
import com.jmvsta.entity.History;
import com.jmvsta.repository.HistoryRepository;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoryService {

    private final HistoryRepository historyRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    private String formatTitle(Currency currency) {
        return currency.getCharCode() + " (" + currency.getName() + ")";
    }

    @Transactional
    public void createHistory(Currency curA, Currency curB, BigDecimal sourceVal, BigDecimal resultVal) {
//        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
//        dateFormat.format(new Date())
        History history = new History(formatTitle(curA), formatTitle(curB), sourceVal, resultVal, new Date());
        historyRepository.saveAndFlush(history);
    }

    public History getById(long id) {
        return historyRepository.findById(id);
    }

    public List<History> getAll() {
        return historyRepository.findAll();
    }
}
