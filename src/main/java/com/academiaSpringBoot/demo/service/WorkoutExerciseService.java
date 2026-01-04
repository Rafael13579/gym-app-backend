package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.model.WorkoutExercise;
import com.academiaSpringBoot.demo.repository.ExerciseRepository;
import com.academiaSpringBoot.demo.repository.WorkoutExerciseRepository;
import com.academiaSpringBoot.demo.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public void addExerciseToWorkout(Long workoutId, Long exerciseId, User user) {

        Workout workout = workoutRepository.findByUserAndId(user, workoutId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found"));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        WorkoutExercise we = new WorkoutExercise();
        we.setWorkout(workout);
        we.setExercise(exercise);

        workoutExerciseRepository.save(we);
    }

    @Transactional
    public void deleteWorkoutExercise(Long workoutId, Long weId, User user) {

        WorkoutExercise we = workoutExerciseRepository.findById(weId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout exercise not found"));

        if (!we.getWorkout().getId().equals(workoutId)
                || !we.getWorkout().getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
        }

        workoutExerciseRepository.delete(we);
    }
}

