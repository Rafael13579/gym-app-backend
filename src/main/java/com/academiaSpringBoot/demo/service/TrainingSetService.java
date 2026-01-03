package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.TrainingSet;
import com.academiaSpringBoot.demo.createDTO.TrainingSetCreateDTO;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.repository.WorkoutRepository;
import com.academiaSpringBoot.demo.responseDTO.TrainingSetResponseDTO;
import com.academiaSpringBoot.demo.repository.ExerciseRepository;
import com.academiaSpringBoot.demo.repository.TrainingSetRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrainingSetService {

    private final WorkoutRepository workoutRepository;
    private final TrainingSetRepository trainingSetRepository;
    private final ExerciseRepository exerciseRepository;

    public TrainingSetService(TrainingSetRepository trainingSetRepository, ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.trainingSetRepository = trainingSetRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    public TrainingSetResponseDTO create(Long exerciseId, TrainingSetCreateDTO dto, User user){
        Exercise exercise = exerciseRepository.findByIdAndWorkoutUser(exerciseId, user)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));


        TrainingSet set = TrainingSet.builder()
                .weight(dto.weight())
                .reps(dto.reps())
                .exercise(exercise)
                .build();

        if(!set.getExercise().getWorkout().getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed operation");
        }

        TrainingSet saved = trainingSetRepository.save(set);

        return new TrainingSetResponseDTO(
                saved.getId(),
                saved.getWeight(),
                saved.getReps()
        );

    }

    @Transactional
    public void deleteSet(Long exerciseId, Long trainingSetId, User user){
        TrainingSet set = trainingSetRepository.findByIdAndExerciseWorkoutUser(trainingSetId, user)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "TrainingSet not found"));

        if(!set.getExercise().getId().equals(exerciseId) || !set.getExercise().getWorkout().getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed operation");
        }

        trainingSetRepository.delete(set);
    }

    @Transactional
    public TrainingSetResponseDTO updateWeight(Long trainingSetId, TrainingSetCreateDTO dto, User user) {
        TrainingSet set = trainingSetRepository.findByIdAndExerciseWorkoutUser(trainingSetId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Set not found"));

        if(!set.getExercise().getWorkout().getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed operation");
        }

        set.setWeight(dto.weight());

        return mapToResponseDTO(trainingSetRepository.save(set));
    }

    @Transactional
    public TrainingSetResponseDTO updateReps(Long trainingSetId, TrainingSetCreateDTO dto, User user) {

        TrainingSet set = trainingSetRepository
                .findByIdAndExerciseWorkoutUser(trainingSetId, user)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Set not found")
                );

        set.setReps(dto.reps());

        return mapToResponseDTO(set);
    }

    public List<TrainingSetResponseDTO> listByExercise(Long exerciseId, User user) {

        Exercise exercise = exerciseRepository
                .findByIdAndWorkoutUser(exerciseId, user)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found")
                );

        return trainingSetRepository.findByExercise(exercise)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public TrainingSetResponseDTO mapToResponseDTO(TrainingSet trainingSet) {
        return new TrainingSetResponseDTO(
                trainingSet.getId(),
                trainingSet.getWeight(),
                trainingSet.getReps()
        );
    }
}
