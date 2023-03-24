package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogDTO;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import com.brian.springreactivedogwalker.usecases.interfaces.AddDogToWalker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddDogToWalkerUseCase implements AddDogToWalker {

    private final IDogWalkerRepository dogWalkerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addToWalker(String wlkId, DogDTO dogDTO) {
        dogWalkerRepository
                .findById(wlkId)
                .switchIfEmpty(Mono.error(new RuntimeException("Walker not found by id " + wlkId)))
                .flatMap(dogWalker -> {
                    var dogInGroup = dogWalker.getDogsGroup();
                    dogInGroup.add(dogDTO);
                    dogWalker.setDogsGroup(dogInGroup);
                    return dogWalkerRepository.save(dogWalker);
                })
                .subscribe();
    }
}