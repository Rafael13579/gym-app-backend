package com.academiaSpringBoot.demo.controller;

import com.academiaSpringBoot.demo.createDTO.ExerciseCreateDTO;
import com.academiaSpringBoot.demo.responseDTO.ExerciseResponseDTO;
import com.academiaSpringBoot.demo.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts/exercises")
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> createExercise(@RequestBody @Valid ExerciseCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId) {
        exerciseService.deleteExercise(exerciseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> listAll() {
        return ResponseEntity.ok(exerciseService.listAll());
    }
}


