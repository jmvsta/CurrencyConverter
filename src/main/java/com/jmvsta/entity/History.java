package com.jmvsta.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="HIS_GEN")
    @SequenceGenerator(name="HIS_GEN", sequenceName="history_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "source_currency_id")
    private Currency sourceCurrency;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency;

    @NonNull
    @Column(nullable = false)
    private BigDecimal sourceSum;

    @NonNull
    @Column(nullable = false)
    private BigDecimal targetSum;

    @NonNull
    @Column(nullable = false)
    private Date date;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
