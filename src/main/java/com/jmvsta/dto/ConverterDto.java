package com.jmvsta.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConverterDto {

    private Long currAId;

    private Long currBId;

    private BigDecimal valA;

    private String date;
}
