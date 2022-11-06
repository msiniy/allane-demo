package se.allane.demo.repositories;

import lombok.Builder;

import java.time.Year;

@Builder
public record VehicleDto (Long id, String brand, String model, Year year) {}