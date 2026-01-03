package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.exception.ResourceNotFoundException;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.createDTO.WorkoutCreateDTO;
import com.academiaSpringBoot.demo.repository.UserRepository;
import com.academiaSpringBoot.demo.repository.WorkoutRepository;
import com.academiaSpringBoot.demo.responseDTO.ExerciseResponseDTO;
import com.academiaSpringBoot.demo.responseDTO.WorkoutResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public WorkoutResponseDTO create(User user, WorkoutCreateDTO dto) {

        Workout workout = Workout.builder()
                        .name(dto.name())
                        .user(user)
                        .build();

        Workout saved = workoutRepository.save(workout);

        return new WorkoutResponseDTO(
                saved.getId(),
                saved.getName(),
                List.of()
        );
    }

    public List<WorkoutResponseDTO> listByUser(User user) {
        return workoutRepository.findByUser(user)
                .stream()
                .map(this::mapResponseToDTO)
                .toList();
    }

    @Transactional
    public void deleteWorkout(Long workoutId, User user) {
        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                        .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));

        workoutRepository.delete(workout);
    }

    @Transactional
    public WorkoutResponseDTO updateWorkoutName(Long workoutId, User user, String name) {
        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));

        workout.setName(name);

        return mapResponseToDTO(workout);
    }

    public WorkoutResponseDTO mapResponseToDTO(Workout workout) {
        List<ExerciseResponseDTO> exercises = List.of();

        if(workout.getExercises() != null) {
            exercises = workout.getExercises()
                    .stream()
                    .map(ex -> new ExerciseResponseDTO(
                            ex.getId(),
                            ex.getName(),
                            ex.getMuscularGroup(),
                            ex.getDescription(),
                            List.of()
                        )
                    ).toList();
        }

        return new WorkoutResponseDTO(
                workout.getId(),
                workout.getName(),
                exercises
        );
    }
}
