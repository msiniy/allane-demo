package se.allane.demo.repositories;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Year;


public record VehicleDetailsDto(Long id, @NotNull Integer version,
                                @Size(min=17, max=17) String vin, @NotNull BigDecimal price,
                                Long brandId, String brandName,
                                @NotNull Long modelId, String modelName, Year modelYear,
                                Long contractId, Long contractNumber) {}