package com.brian.springreactivedogwalker.consumer;

import com.brian.springreactivedogwalker.domain.DTO.DogDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DogEvent {
    private String dogWlkId;
    private DogDTO dogToAdd;
    private String eventType;
}
