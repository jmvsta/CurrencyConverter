package com.jmvsta.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name="Valute")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValuteDto {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "NumCode")
    private String numCode;

    @XmlElement(name = "CharCode")
    private String charCode;

    @XmlElement(name = "Nominal")
    private String nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    private String value;

}