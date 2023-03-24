package com.brian.springreactivedogwalker.consumer;

import com.brian.springreactivedogwalker.config.RabbitMQConfig;
import com.brian.springreactivedogwalker.usecases.AddDogToWalkerUseCase;
import com.brian.springreactivedogwalker.usecases.RemoveDogToWalkerUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WalkerConsumer {

    private final ObjectMapper objectMapper;
    private final AddDogToWalkerUseCase addDogToWalkerUseCase;
    private final RemoveDogToWalkerUseCase removeDogToWalkerUseCase;


    @RabbitListener(queues = RabbitMQConfig.DOG_QUEUE)
    public void consume(String message) throws JsonProcessingException {
        DogEvent event = objectMapper.readValue(message, DogEvent.class);
        if (event.getEventType().equals("DogAdded")){
            addDogToWalkerUseCase.addToWalker(event.getDogWlkId(), event.getDogToAdd());
        }
        if (event.getEventType().equals("DogRemove")){
           removeDogToWalkerUseCase.removeToWalker(event.getDogWlkId(), event.getDogToAdd());
        }
    }
}