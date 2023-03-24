package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllDogWalkersUseCase implements Supplier<Flux<DogWalkerDTO>> {
    private final IDogWalkerRepository dogWalkerRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<DogWalkerDTO> get() {
        return this.dogWalkerRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No Dogs Walker available")))
                .map(dogWalker -> mapper.map(dogWalker, DogWalkerDTO.class))
                .onErrorResume(Mono::error);
    }
}
