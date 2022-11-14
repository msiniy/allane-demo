package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;

@AllArgsConstructor
@Getter
public class ModelAndBrandDto {
    private final Long modelId;
    private final String modelName;
    private final Year modelYear;
    private final Long brandId;
    private final String brandName;
}
