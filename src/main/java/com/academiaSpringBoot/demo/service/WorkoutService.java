package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.createDTO.WorkoutCreateDTO;
import com.academiaSpringBoot.demo.exception.ResourceNotFoundException;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.model.WorkoutExercise;
import com.academiaSpringBoot.demo.repository.WorkoutRepository;
import com.academiaSpringBoot.demo.responseDTO.TrainingSetResponseDTO;
import com.academiaSpringBoot.demo.responseDTO.WorkoutExerciseResponseDTO;
import com.academiaSpringBoot.demo.responseDTO.WorkoutResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public WorkoutResponseDTO create(User user, WorkoutCreateDTO dto) {

        Workout workout = Workout.builder()
                .name(dto.name())
                .user(user)
                .build();

        Workout saved = workoutRepository.save(workout);
        return mapToResponseDTO(saved);
    }

    public List<WorkoutResponseDTO> listByUser(User user) {
        return workoutRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional
    public WorkoutResponseDTO updateWorkoutName(Long workoutId, User user, String name) {

        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));

        workout.setName(name);
        return mapToResponseDTO(workout);
    }

    @Transactional
    public void deleteWorkout(Long workoutId, User user) {

        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));

        workoutRepository.delete(workout);
    }

    private WorkoutResponseDTO mapToResponseDTO(Workout workout) {

        List<WorkoutExerciseResponseDTO> exercises =
                workout.getWorkoutExercises()
                        .stream()
                        .map(this::mapWorkoutExercise)
                        .toList();

        return new WorkoutResponseDTO(
                workout.getId(),
                workout.getName(),
                exercises
        );
    }

    private WorkoutExerciseResponseDTO mapWorkoutExercise(WorkoutExercise we) {

        List<TrainingSetResponseDTO> sets =
                we.getTrainingSets()
                        .stream()
                        .map(ts -> new TrainingSetResponseDTO(
                                ts.getId(),
                                ts.getWeight(),
                                ts.getReps()
                        ))
                        .toList();

        return new WorkoutExerciseResponseDTO(
                we.getId(),
                we.getExercise().getId(),
                we.getExercise().getName(),
                we.getExercise().getMuscularGroup(),
                we.getExercise().getDescription(),
                sets
        );
    }
}

