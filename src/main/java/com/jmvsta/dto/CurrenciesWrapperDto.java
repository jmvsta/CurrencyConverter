package com.jmvsta.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlRootElement(name="ValCurs")
@XmlAccessorType(XmlAccessType.NONE)
public class CurrenciesWrapperDto {

    @XmlAttribute(name="name")
    private String name;

    @XmlAttribute(name="date")
    private String date;

    @XmlElement(name="Valute")
    private List<CurrencyDto> valute;

}