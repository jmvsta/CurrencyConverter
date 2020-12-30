package com.jmvsta.entity;

import java.math.BigDecimal;
import javax.persistence.Basic;
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
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="currency_seq")
    @SequenceGenerator(name="currency_seq", sequenceName="currency_seq")
    private Long id;

    @NonNull
    @Basic(optional = false)
    private String valuteId;

    @NonNull
    @Basic(optional = false)
    private int numCode;

    @NonNull
    @Basic(optional = false)
    private String charCode;

    @NonNull
    @Basic(optional = false)
    private String name;

    @NonNull
    @Basic(optional = false)
    @Column(nullable= false, precision=20, scale=4)
    private BigDecimal value;

}
