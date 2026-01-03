package com.academiaSpringBoot.demo.responseDTO;

import java.util.List;

public record ExerciseResponseDTO(Long Id,
                                  String name,
                                  String muscularGroup,
                                  String description,
                                  List<TrainingSetResponseDTO> trainingSets) {
}
