package com.academiaSpringBoot.demo.createDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExerciseCreateDTO(@NotBlank String name,
                                @NotBlank String muscularGroup,
                                @NotBlank String description,
                                @NotNull Long workoutId) {}







