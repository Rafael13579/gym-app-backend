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
@RequestMapping("exercise/{exerciseId}/trainingSet")
@CrossOrigin
public class TrainingSetController {

    private final TrainingSetService trainingSetService;

    public TrainingSetController(TrainingSetService trainingSetService){
        this.trainingSetService = trainingSetService;
    }

    @PostMapping
    public ResponseEntity<TrainingSetResponseDTO> createTrainingSet(@PathVariable Long exerciseId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(trainingSetService.create(exerciseId, dto, user));
    }

    @DeleteMapping("/{trainingSetId}")
    public ResponseEntity<Void> deleteTrainingSet(@PathVariable Long trainingSetId, @PathVariable Long exerciseId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        trainingSetService.deleteSet(exerciseId, trainingSetId, user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{trainingSetId}")
    public ResponseEntity<TrainingSetResponseDTO> updateWeight(@PathVariable Long trainingSetId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.updateWeight(trainingSetId, dto, user));
    }

    @PutMapping("/trainingSetId")
    public ResponseEntity<TrainingSetResponseDTO> updateReps(@PathVariable Long trainingSetId, @RequestBody @Valid TrainingSetCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.updateReps(trainingSetId, dto, user));
    }

    @GetMapping
    public ResponseEntity<List<TrainingSetResponseDTO>> listTrainingSets(@PathVariable Long exerciseId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(trainingSetService.listByExercise(exerciseId, user));
    }

}
