package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.domain.collection.DogWalker;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import com.brian.springreactivedogwalker.usecases.interfaces.UpdateDogWalker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateDogWalkerUseCase implements UpdateDogWalker {
    private final IDogWalkerRepository dogWalkerRepository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<DogWalkerDTO> update(String id, DogWalkerDTO dogWalkerDTO) {
        return this.dogWalkerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(dogWalker -> {
                    dogWalkerDTO.setId(dogWalker.getId());
                    return dogWalkerRepository.save(modelMapper.map(dogWalkerDTO, DogWalker.class));
                })
                .map(dogWalker -> modelMapper.map(dogWalker, DogWalkerDTO.class))
                .onErrorResume(error -> Mono.error(new RuntimeException("Dog walker not found")));
    }
}
