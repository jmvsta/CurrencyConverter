package com.jmvsta.repository;

import com.jmvsta.entity.ExchangeRate;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findById(long id);

    ExchangeRate findAllByCurrency_ValuteIdAndDate(String valuteId, Date date);

}
