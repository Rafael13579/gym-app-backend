package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.model.Exercise;
import com.academiaSpringBoot.demo.model.User;
import com.academiaSpringBoot.demo.model.Workout;
import com.academiaSpringBoot.demo.model.WorkoutExercise;
import com.academiaSpringBoot.demo.repository.WorkoutExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutExerciseServiceTest {

    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;


    @InjectMocks
    private WorkoutExerciseService workoutExerciseService;

    @Test
    void shouldAddWorkoutExerciseSuccefully() {
        User user = new User();
        user.setId(1L);

        Workout workout = new Workout();
        workout.setId(1L);

        Exercise exercise = new Exercise();
        exercise.setId(1L);

        WorkoutExercise savedWe = new WorkoutExercise();
        savedWe.setWorkout(workout);
        savedWe.setExercise(exercise);

        when(workoutExerciseRepository.save(any(WorkoutExercise.class)))
                .thenReturn(savedWe);

        workoutExerciseService.addExerciseToWorkout(workout.getId(), exercise.getId(), user);

        assertEquals(savedWe.getWorkout(), workout);
        assertEquals(savedWe.getExercise(), exercise);

        verify(workoutExerciseRepository).save(any(WorkoutExercise.class));

    }
}
