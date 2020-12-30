package com.jmvsta.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "history")
@NoArgsConstructor
@RequiredArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="history_seq")
    @SequenceGenerator(name="history_seq", sequenceName="history_seq")
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String sourceCurrencyTitle;

    @NonNull
    @Column(nullable = false)
    private String targetCurrencyTitle;

    @NonNull
    @Column(nullable = false)
    private BigDecimal sourceSum;

    @NonNull
    @Column(nullable = false)
    private BigDecimal targetSum;

    @NonNull
    @Column(nullable = false)
    private Date date;


}
