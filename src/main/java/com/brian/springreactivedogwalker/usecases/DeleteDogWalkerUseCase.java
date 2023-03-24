package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import com.brian.springreactivedogwalker.usecases.interfaces.DeleteDogWalker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteDogWalkerUseCase implements DeleteDogWalker {
    private final IDogWalkerRepository dogWalkerRepository;

    @Override
    public Mono<Void> delete(String id) {
        return dogWalkerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Dog walker not found")))
                .flatMap(dogWalkerRepository::delete)
                .onErrorResume(Mono::error);

    }
}
