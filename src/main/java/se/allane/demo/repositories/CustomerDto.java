package se.allane.demo.repositories;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
public record CustomerDto(Long id, @NotNull Integer version, @NotNull @Size(max=255) String firstName,
                          @NotNull @Size(max=255) String lastName,
                          @NotNull @Past LocalDate birthDate) {
}
