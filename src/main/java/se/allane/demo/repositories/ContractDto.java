package se.allane.demo.repositories;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Year;

@Builder
public record ContractDto(Long id, Integer version, Long contractNumber, BigDecimal monthlyRate,
                          String customerFirstName, String customerLastName,
                          String vehicleVin, BigDecimal vehiclePrice,
                          String brandName, String modelName, Year modelYear) {
}
