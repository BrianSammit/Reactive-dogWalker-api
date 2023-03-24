package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.domain.collection.DogWalker;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class UpdateDogWalkerUseCaseTest {

    @Mock
    IDogWalkerRepository repository;
    ModelMapper modelMapper;
    UpdateDogWalkerUseCase updateDogWalkerUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateDogWalkerUseCase = new UpdateDogWalkerUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("update_Success")
    void updateDog() {

        DogWalker dogWalker = new DogWalker();
        dogWalker.setName("Test name");
        dogWalker.setLastname("Test last name");
        dogWalker.setAge(17);

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dogWalker);
                });
        Mockito.when(repository.save(dogWalker)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dogWalker);
                });

        Mono<DogWalkerDTO> response = updateDogWalkerUseCase.update("Test id", modelMapper.map(dogWalker, DogWalkerDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dogWalker, DogWalkerDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).save(dogWalker);
        Mockito.verify(repository).findById("Test id");
    }


}
