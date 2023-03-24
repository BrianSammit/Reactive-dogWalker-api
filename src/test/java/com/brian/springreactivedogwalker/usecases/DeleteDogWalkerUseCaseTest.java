package com.brian.springreactivedogwalker.usecases;

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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class DeleteDogWalkerUseCaseTest {

    @Mock
    IDogWalkerRepository repository;
    DeleteDogWalkerUseCase deleteDogWalkerUseCase;

    @BeforeEach
    void init() {
        deleteDogWalkerUseCase = new DeleteDogWalkerUseCase(repository);
    }

    @Test
    @DisplayName("delete_Success")
    void deleteDog() {

        DogWalker dogWalker = new DogWalker();
        dogWalker.setName("Test name");
        dogWalker.setLastname("Test last name");
        dogWalker.setAge(17);

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dogWalker);
                });

        Mockito.when(repository.delete(dogWalker)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.empty();
                });

        var response = deleteDogWalkerUseCase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).delete(dogWalker);
        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
    }

}