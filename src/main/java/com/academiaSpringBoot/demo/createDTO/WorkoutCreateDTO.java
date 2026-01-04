package com.academiaSpringBoot.demo.createDTO;

import jakarta.validation.constraints.NotBlank;

public record WorkoutCreateDTO(@NotBlank String name) {}

