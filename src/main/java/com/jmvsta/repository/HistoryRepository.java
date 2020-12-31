package com.jmvsta.repository;

import com.jmvsta.entity.History;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

    History findById(long id);

    List<History> findAllByUser_Id(Long id);

    List<History> findAllByUser_IdAndDate(Long id, Date date);

    List<History> findAllByUser_IdAndDateAndSourceCurrency_ValuteId(Long id, Date date, String vid);

    List<History> findAllByUser_IdAndSourceCurrency_ValuteId(Long id, String vid);

    List<History> findAllByUser_IdAndDateAndTargetCurrency_ValuteId(Long id, Date date, String vid);

    List<History> findAllByUser_IdAndTargetCurrency_ValuteId(Long id, String vid);

    List<History> findAllByUser_IdAndSourceCurrency_ValuteIdAndTargetCurrency_ValuteId(Long id, String sid, String tid);


}
