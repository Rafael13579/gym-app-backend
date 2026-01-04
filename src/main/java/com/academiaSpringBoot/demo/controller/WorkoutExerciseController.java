package com.academiaSpringBoot.demo.controller;

import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.service.WorkoutExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts/{workoutId}/exercises")
@CrossOrigin
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping("/{exerciseId}")
    public ResponseEntity<Void> addExerciseToWorkout(@PathVariable Long workoutId, @PathVariable Long exerciseId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        workoutExerciseService.addExerciseToWorkout(workoutId, exerciseId, user);

        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{weId}")
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable Long workoutId, @PathVariable Long weId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        workoutExerciseService.deleteWorkoutExercise(workoutId, weId, user);

        return ResponseEntity.noContent().build();
    }
}
