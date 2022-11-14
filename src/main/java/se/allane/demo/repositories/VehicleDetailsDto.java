package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Year;

@AllArgsConstructor
@Getter
public class VehicleDetailsDto {

    private final Long id;
    @NotNull
    private final Integer version;
    @Size(min = 17, max = 17)
    private final String vin;
    @NotNull
    private final BigDecimal price;
    private final Long brandId;
    private final String brandName;

    @NotNull
    private final Long modelId;
    private final String modelName;
    private final Year modelYear;

    private final Long contractId;
    private final Long contractNumber;


}