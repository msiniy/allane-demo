package se.allane.demo.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class CustomerDto {
    private final Long id;
    @NotNull
    private final Integer version;
    @NotNull
    @Size(max = 255)
    private final String firstName;
    @NotNull
    @Size(max = 255)
    private final String lastName;
    @NotNull
    @Past
    private final LocalDate birthDate;
}
