package se.allane.demo.repositories;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Year;

@Builder
public record ContractDto(Long id, @NotNull Integer version, @NotNull Long contractNumber,
                          @NotNull BigDecimal monthlyRate,
                          @NotNull Long customerId, String customerFirstName, String customerLastName,
                          @NotNull Long vehicleId, String vehicleVin, BigDecimal vehiclePrice,
                          String brandName, String modelName, Year modelYear) {
}
