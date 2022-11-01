package se.allane.demo.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VehicleDetailsDto extends VehicleDto {
    private final Integer version;
    private final String vin;
    private final Long contractId;
    private final Long contractNumber;
}
