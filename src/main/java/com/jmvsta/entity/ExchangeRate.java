package com.jmvsta.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "exchangerate")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="EXH_SEQ")
    @SequenceGenerator(name="EXH_SEQ", sequenceName="exchange_rate_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @Basic(optional = false)
    @Column(nullable= false, precision=20, scale=4)
    private BigDecimal value;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "valute_id")
    private Currency currency;

    @NonNull
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

}
