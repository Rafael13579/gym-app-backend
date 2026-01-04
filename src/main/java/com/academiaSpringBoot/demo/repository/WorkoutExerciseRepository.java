package com.academiaSpringBoot.demo.repository;

import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    Optional<WorkoutExercise> findByWorkoutAndExercise(Workout workout, Exercise exercise);

    List<WorkoutExercise> findByWorkout(Workout workout);
}

