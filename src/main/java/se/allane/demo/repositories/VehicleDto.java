package se.allane.demo.repositories;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Year;

@Builder
public record VehicleDto (Long id, String brandName, String modelName, Year modelYear, String vin, BigDecimal price) {}