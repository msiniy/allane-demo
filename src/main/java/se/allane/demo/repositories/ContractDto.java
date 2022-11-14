package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Year;

@Builder
@AllArgsConstructor
@Getter
public class ContractDto {

    private final Long id;

    @NotNull
    private final Integer version;
    @NotNull
    private final Long contractNumber;
    @NotNull
    private final BigDecimal monthlyRate;

    @NotNull
    private final  Long customerId;
    private final String customerFirstName;
    private final String customerLastName;

    @NotNull
    private final Long vehicleId;
    private final String vehicleVin;
    private final BigDecimal vehiclePrice;

    private final String brandName;
    private final String modelName;
    private final Year modelYear;
}
