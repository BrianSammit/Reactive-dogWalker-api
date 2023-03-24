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
class SaveDogWalkerUseCaseTest {

    @Mock
    IDogWalkerRepository repository;
    ModelMapper modelMapper;
    SaveDogWalkerUseCase saveDogWalkerUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveDogWalkerUseCase = new SaveDogWalkerUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("save_Success")
    void saveDogWalker() {

        DogWalker dogWalker = new DogWalker();
        dogWalker.setName("Test name");
        dogWalker.setLastname("Test lastname");
        dogWalker.setAge(20);

        Mockito.when(repository.save(dogWalker)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dogWalker);
                });

        Mono<DogWalkerDTO> response = saveDogWalkerUseCase.save(modelMapper.map(dogWalker, DogWalkerDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dogWalker,DogWalkerDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).save(dogWalker);
    }
//    @Test
//    @DisplayName("save_error")
//    public void testSaveDogWalkerError() {
//
//        DogWalker dogWalker = new DogWalker();
//        dogWalker.setName("Test name");
//        dogWalker.setLastname("Test lastname");
//        dogWalker.setAge(20);
//
//       Mockito.when(repository.save(dogWalker)).thenReturn(Mono.empty());
//
//        Mono<DogWalkerDTO> result = saveDogWalkerUseCase.save(modelMapper.map(dogWalker, DogWalkerDTO.class));
//
//        StepVerifier.create(result)
//                .expectError(Throwable.class)
//                .verify();
//
//        Mockito.verify(repository).save(ArgumentMatchers.any(DogWalker.class));
//    }

}
