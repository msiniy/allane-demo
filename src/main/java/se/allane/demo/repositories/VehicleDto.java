package se.allane.demo.repositories;

import java.time.Year;

public record VehicleDto (Long id, String brand, String model, Year year) {}