package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Year;

@Builder
@AllArgsConstructor
@Getter
public class VehicleDto {

    private final Long id;
    private final String brandName;
    private final String modelName;
    private final Year modelYear;
    private final String vin;
    private final BigDecimal price;
}