package se.allane.demo.repositories;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerDto(Long id, Integer version, String firstName, String lastName, LocalDate birthDate) {
}
