package com.brian.springreactivedogwalker.usecases.interfaces;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveDogWalker {
    Mono<DogWalkerDTO> save(DogWalkerDTO dogDTO);
}
