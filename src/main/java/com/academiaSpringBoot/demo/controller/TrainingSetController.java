package com.academiaSpringBoot.demo.controller;

import com.academiaSpringBoot.demo.createDTO.TrainingSetCreateDTO;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.responseDTO.TrainingSetResponseDTO;
import com.academiaSpringBoot.demo.service.TrainingSetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping
public class TrainingSetController {

    private final TrainingSetService trainingSetService;

    public TrainingSetController(TrainingSetService trainingSetService) {
        this.trainingSetService = trainingSetService;
    }

    @PostMapping("/workouts/{workoutId}/exercises/{exerciseId}/sets")
    public ResponseEntity<TrainingSetResponseDTO> create(@PathVariable Long workoutId, @PathVariable Long exerciseId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        TrainingSetResponseDTO response = trainingSetService.create(workoutId, exerciseId, dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/workout-exercises/{workoutExerciseId}/sets")
    public ResponseEntity<List<TrainingSetResponseDTO>> list(@PathVariable Long workoutExerciseId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.listByWorkoutExercise(workoutExerciseId, user));
    }

    @PutMapping("/training-sets/{trainingSetId}/weight")
    public ResponseEntity<TrainingSetResponseDTO> updateWeight(@PathVariable Long trainingSetId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.updateWeight(trainingSetId, dto, user));
    }

    @PutMapping("/training-sets/{trainingSetId}/reps")
    public ResponseEntity<TrainingSetResponseDTO> updateReps(@PathVariable Long trainingSetId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.updateReps(trainingSetId, dto, user));
    }


    @DeleteMapping("/workout-exercises/{workoutExerciseId}/sets/{trainingSetId}")
    public ResponseEntity<Void> delete(@PathVariable Long workoutExerciseId, @PathVariable Long trainingSetId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        trainingSetService.deleteSet(workoutExerciseId, trainingSetId, user);

        return ResponseEntity.noContent().build();
    }
}

