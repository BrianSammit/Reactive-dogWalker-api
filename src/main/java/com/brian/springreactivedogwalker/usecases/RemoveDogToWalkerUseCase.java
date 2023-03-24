package com.brian.springreactivedogwalker.usecases;

import com.brian.springreactivedogwalker.domain.DTO.DogDTO;
import com.brian.springreactivedogwalker.repository.IDogWalkerRepository;
import com.brian.springreactivedogwalker.usecases.interfaces.RemoveDogToWalker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RemoveDogToWalkerUseCase implements RemoveDogToWalker {

    private final ModelMapper modelMapper;
    private final IDogWalkerRepository dogWalkerRepository;

    @Override
    public void removeToWalker(String wlkId, DogDTO dogDTO) {
        dogWalkerRepository
                .findById(wlkId)
                .switchIfEmpty(Mono.error(new RuntimeException("Walker not found for id " + wlkId)))
                .flatMap(dogWalker -> {
                    var dogInTeam = dogWalker.getDogsGroup();
                    dogInTeam.removeIf(dogDTO1 -> dogDTO1.getId().equals(dogDTO.getId()));
                    dogWalker.setDogsGroup(dogInTeam);
                    return dogWalkerRepository.save(dogWalker);
                })
                .subscribe();
    }
}