package com.jmvsta.repository;

import com.jmvsta.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findCurrencyByValuteId(String id);

    Currency findByCharCode(String id);

}
