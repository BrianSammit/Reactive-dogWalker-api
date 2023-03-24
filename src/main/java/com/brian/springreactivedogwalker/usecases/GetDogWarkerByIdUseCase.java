package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetDogWarkerByIdUseCase implements Function<String, Mono<DogWalkerDTO>> {
    private final IDogWalkerRepository dogWalkerRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DogWalkerDTO> apply(String id) {
        return this.dogWalkerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Dog walker not found")))
                .map(dogWalker -> mapper.map(dogWalker, DogWalkerDTO.class))
                .onErrorResume(Mono::error);
    }
}
