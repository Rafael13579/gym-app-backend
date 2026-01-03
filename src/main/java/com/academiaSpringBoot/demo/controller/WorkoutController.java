package com.academiaSpringBoot.demo.controller;


import com.academiaSpringBoot.demo.createDTO.WorkoutCreateDTO;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.responseDTO.WorkoutResponseDTO;
import com.academiaSpringBoot.demo.service.WorkoutService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> create(@RequestBody @Valid WorkoutCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(workoutService.create(user, dto));

    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        workoutService.deleteWorkout(workoutId, user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDTO> updateWorkoutName(@RequestBody @Valid WorkoutCreateDTO dto, @PathVariable Long workoutId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(workoutService.updateWorkoutName(workoutId, user, dto.name()));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDTO>> list(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(workoutService.listByUser(user));
    }
}
