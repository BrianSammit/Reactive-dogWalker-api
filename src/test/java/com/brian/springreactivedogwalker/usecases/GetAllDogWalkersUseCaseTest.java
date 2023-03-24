package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.domain.collection.DogWalker;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetAllDogWalkersUseCaseTest {

    @Mock
    IDogWalkerRepository repository;
    ModelMapper modelMapper;
    GetAllDogWalkersUseCase getAllDogWalkersUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        getAllDogWalkersUseCase = new GetAllDogWalkersUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAll_Success")
    void getAllDogWalkers() {

        DogWalker dogWalker = new DogWalker();
        dogWalker.setName("Test name");
        dogWalker.setLastname("Test last name");
        dogWalker.setAge(17);

        DogWalker dogWalker1 = new DogWalker();
        dogWalker.setName("Test name 2");
        dogWalker.setLastname("Test last name 2");
        dogWalker.setAge(50);

        Mockito.when(repository.findAll()).
                thenAnswer(InvocationOnMock -> {
                    return Flux.just(dogWalker, dogWalker1);
                });

        Flux<DogWalkerDTO> response = getAllDogWalkersUseCase.get();

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dogWalker, DogWalkerDTO.class))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }
}
