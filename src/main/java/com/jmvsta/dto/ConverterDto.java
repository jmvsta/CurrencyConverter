package com.jmvsta.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ConverterDto {

    private String sourceCurrId;

    private String targetCurrId;

    private BigDecimal sourceValue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
