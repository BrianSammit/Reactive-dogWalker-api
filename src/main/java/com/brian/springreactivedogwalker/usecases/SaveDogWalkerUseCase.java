package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.domain.collection.DogWalker;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import com.brian.springreactivedogwalker.usecases.interfaces.SaveDogWalker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveDogWalkerUseCase implements SaveDogWalker {

    private final IDogWalkerRepository dogWalkerRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DogWalkerDTO> save(DogWalkerDTO dogWalkerDTO) {
        return this.dogWalkerRepository
                .save(mapper.map(dogWalkerDTO, DogWalker.class))
                .switchIfEmpty(Mono.empty())
                .map(dogWalker -> mapper.map(dogWalker, DogWalkerDTO.class))
                .onErrorResume(Mono::error);
    }
}
