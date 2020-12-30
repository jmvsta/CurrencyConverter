package com.jmvsta.repository;

import com.jmvsta.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History findById(long id);

}
