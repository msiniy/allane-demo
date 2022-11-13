package se.allane.demo.repositories;

import java.time.Year;

public record ModelAndBrandDto(Long modelId, String modelName, Year modelYear, Long brandId, String brandName) {
}
