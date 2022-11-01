package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Year;

@Data
@AllArgsConstructor
@SuperBuilder
public class VehicleDto {
    private final Long id;
    private final String brand;
    private final String model;
    private final Year year;
}