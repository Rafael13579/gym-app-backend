package com.academiaSpringBoot.demo.repository;

import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.TrainingSet;
import com.academiaSpringBoot.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.*;
import java.util.Optional;

public interface TrainingSetRepository extends JpaRepository<TrainingSet, Long> {

    Optional<TrainingSet> findByIdAndExerciseWorkoutUser(Long id, User user);

    List<TrainingSet> findByExercise(Exercise exercise);
}
