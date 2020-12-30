package com.jmvsta.service;

import com.jmvsta.entity.Currency;
import com.jmvsta.repository.CurrencyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Currency getById(long id) {
        return currencyRepository.findById(id);
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }
}
