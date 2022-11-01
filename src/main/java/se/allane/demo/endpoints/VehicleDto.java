package se.allane.demo.endpoints;

import lombok.Builder;
@Builder
public record VehicleDto(Long id, String brand, String model, int year, String vin) {
}
