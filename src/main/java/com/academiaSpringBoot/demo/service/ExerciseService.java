package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.exception.ResourceNotFoundException;
import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.repository.ExerciseRepository;
import com.academiaSpringBoot.demo.repository.WorkoutRepository;
import com.academiaSpringBoot.demo.responseDTO.ExerciseResponseDTO;
import com.academiaSpringBoot.demo.responseDTO.TrainingSetResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.academiaSpringBoot.demo.createDTO.ExerciseCreateDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    public ExerciseResponseDTO create(User user, ExerciseCreateDTO dto) {
        Workout workout = workoutRepository.findById(dto.workoutId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found"));

        if(!workout.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O workout não pertence ao usuário");
        }

        Exercise exercise = Exercise.builder()
                .name(dto.name())
                .muscularGroup(dto.muscularGroup())
                .description(dto.description())
                .workout(workout)
                .build();

        Exercise saved = exerciseRepository.save(exercise);

        return new ExerciseResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getMuscularGroup(),
                saved.getDescription(),
                List.of()
        );
    }

    @Transactional
    public void deleteExercise(Long workoutId, Long exerciseId, User user) {
        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found"));

        Exercise exercise = exerciseRepository.findByWorkoutAndId(workout, exerciseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        exerciseRepository.delete(exercise);
    }


    public List<ExerciseResponseDTO> listByWorkout(Long workoutId) {
        return exerciseRepository.findByWorkoutId(workoutId)
                .stream()
                .map(this::mapResponseToDTO)
                .toList();
    }


    public ExerciseResponseDTO mapResponseToDTO(Exercise exercise) {
        List<TrainingSetResponseDTO> sets = List.of();

        if(exercise.getSets() != null) {
            sets = exercise.getSets()
                    .stream()
                    .map(ts -> new TrainingSetResponseDTO(
                            ts.getId(),
                            ts.getWeight(),
                            ts.getReps()
                    ))
                    .toList();

        }

        return new ExerciseResponseDTO(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscularGroup(),
                exercise.getDescription(),
                sets
        );
    }
}
