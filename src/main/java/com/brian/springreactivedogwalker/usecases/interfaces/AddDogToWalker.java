package com.brian.springreactivedogwalker.usecases.interfaces;

import com.brian.springreactivedogwalker.domain.DTO.DogDTO;

@FunctionalInterface
public interface AddDogToWalker {
    void addToWalker(String wlkId, DogDTO dogDTO);
}
