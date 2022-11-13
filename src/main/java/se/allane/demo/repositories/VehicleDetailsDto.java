package se.allane.demo.repositories;

import java.math.BigDecimal;
import java.time.Year;


public record VehicleDetailsDto(Long id, Integer version, String vin, BigDecimal price,
                                Long brandId, String brandName,
                                Long modelId, String modelName, Year modelYear,
                                Long contractId, Long contractNumber) {}