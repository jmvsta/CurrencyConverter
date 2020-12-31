package com.jmvsta.service;

import com.jmvsta.dto.ConverterDto;
import com.jmvsta.entity.History;
import com.jmvsta.repository.HistoryRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoryService {

    private final HistoryRepository historyRepository;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    private boolean stringNullOfEmpty(String string) {
        return string == null || "".equals(string);
    }

    public List<History> getAllByUserIdAndFilter(Long userId, ConverterDto filter) {
        if (stringNullOfEmpty(filter.getSourceCurrId())
                && stringNullOfEmpty(filter.getTargetCurrId())
                && filter.getDate() == null) {
            return historyRepository.findAllByUser_Id(userId);
        } else if (stringNullOfEmpty(filter.getSourceCurrId())
                && stringNullOfEmpty(filter.getTargetCurrId())) {
            return historyRepository.findAllByUser_IdAndDate(userId, filter.getDate());
        } else if (stringNullOfEmpty(filter.getSourceCurrId())) {
            return filter.getDate() == null
                    ? historyRepository.findAllByUser_IdAndTargetCurrency_ValuteId(userId, filter.getTargetCurrId())
                    : historyRepository.findAllByUser_IdAndDateAndTargetCurrency_ValuteId(userId, filter.getDate(), filter.getTargetCurrId());
        } else if (stringNullOfEmpty(filter.getTargetCurrId())) {
            return filter.getDate() == null
                    ? historyRepository.findAllByUser_IdAndSourceCurrency_ValuteId(userId, filter.getSourceCurrId())
                    : historyRepository.findAllByUser_IdAndDateAndSourceCurrency_ValuteId(userId, filter.getDate(), filter.getTargetCurrId());
        } else if (!stringNullOfEmpty(filter.getSourceCurrId())
                && !stringNullOfEmpty(filter.getTargetCurrId())) {
            return historyRepository.findAllByUser_IdAndSourceCurrency_ValuteIdAndTargetCurrency_ValuteId(
                    userId, filter.getSourceCurrId(), filter.getTargetCurrId());
        }
        return null;
    }

    public List<History> getAllByUserId(Long userId) {
        return historyRepository.findAllByUser_Id(userId);
    }

    public History getById(long id) {
        return historyRepository.findById(id);
    }

    public List<History> getAll() {
        return historyRepository.findAll();
    }

    @Transactional
    public void addHistory(History history) {
        historyRepository.saveAndFlush(history);
    }
}
