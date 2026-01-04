package com.academiaSpringBoot.demo.service;

import com.academiaSpringBoot.demo.createDTO.TrainingSetCreateDTO;
import com.academiaSpringBoot.demo.model.*;
import com.academiaSpringBoot.demo.repository.TrainingSetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingSetServiceTest{

    @Mock
    private TrainingSetRepository trainingSetRepository;

    @InjectMocks
    private TrainingSetService trainingSetService;

    @Test
    void shouldCreateTrainingSetSuccessfully() {
        User user = new User();
        user.setId(1L);

        Exercise exercise = new Exercise();
        exercise.setId(1L);

        Workout workout = new Workout();
        workout.setId(1L);

        WorkoutExercise we = new WorkoutExercise();
        we.setId(1L);

        TrainingSetCreateDTO dto = new TrainingSetCreateDTO(20, 12);

        TrainingSet savedTrainingSet = new TrainingSet();
        savedTrainingSet.setWeight(dto.weight());
        savedTrainingSet.setReps(dto.reps());
        savedTrainingSet.setWorkoutExercise(we);

        when(trainingSetRepository.save(any(TrainingSet.class)))
                .thenReturn(savedTrainingSet);

        assertEquals(20, savedTrainingSet.getWeight());

        assertEquals(12, savedTrainingSet.getReps());
        assertEquals(we.getId(), savedTrainingSet.getWorkoutExercise().getId());

        trainingSetService.create(workout.getId(), exercise.getId(), dto, user);
    }


}
