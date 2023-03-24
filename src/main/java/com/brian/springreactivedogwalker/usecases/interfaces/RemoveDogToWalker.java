package com.brian.springreactivedogwalker.usecases.interfaces;

import com.brian.springreactivedogwalker.domain.DTO.DogDTO;

@FunctionalInterface
public interface RemoveDogToWalker {
    void removeToWalker(String wlkId, DogDTO dogDTO);
}
