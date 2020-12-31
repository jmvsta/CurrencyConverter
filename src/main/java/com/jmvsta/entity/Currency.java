package com.jmvsta.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @Basic(optional = false)
    @Column(name="valute_id")
    private String valuteId;

    @Basic(optional = false)
    private int numCode;

    @Basic(optional = false)
    private String charCode;

    @Basic(optional = false)
    private String name;

}
