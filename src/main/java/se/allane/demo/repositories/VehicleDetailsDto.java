package se.allane.demo.repositories;

import java.math.BigDecimal;
import java.time.Year;


public record VehicleDetailsDto(Long id, Integer version, String vin, BigDecimal price,
                                String brand, String model, Year year, Long contractId, Long contractNumber) {}