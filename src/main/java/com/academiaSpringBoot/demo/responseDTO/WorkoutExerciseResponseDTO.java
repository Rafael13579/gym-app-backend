package com.academiaSpringBoot.demo.responseDTO;

import java.util.List;

public record WorkoutExerciseResponseDTO(
        Long id,
        Long exerciseId,
        String exerciseName,
        String muscularGroup,
        String description,
        List<TrainingSetResponseDTO> sets
) {}