package com.brian.springreactivedogwalker.router;

import com.brian.springreactivedogwalker.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedogwalker.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DogWalkerRouter {
    @Bean
    public RouterFunction<ServerResponse> saveDogWalker(SaveDogWalkerUseCase saveDogWalkerUseCase){
        return route(POST("/dogWalker").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogWalkerDTO.class)
                        .flatMap(dogWalkerDTO -> saveDogWalkerUseCase.save(dogWalkerDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE)
                                        .bodyValue(throwable.getMessage()))));
    }


    @Bean
    public RouterFunction<ServerResponse> getAllDogsWalker(GetAllDogWalkersUseCase getAllDogWalkersUseCase) {
        return route(GET("/dogWalker"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllDogWalkersUseCase.get(), DogWalkerDTO.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getDogsWalkerById(GetDogWarkerByIdUseCase getDogWarkerByIdUseCase) {
        return route(GET("/dogWalker/{id}"),
                request -> getDogWarkerByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(dogWalkerDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dogWalkerDTO))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteDogWalker(DeleteDogWalkerUseCase deleteDogWalkerUseCase){
        return  route(DELETE("/dogWalker/{id}"),
                request -> deleteDogWalkerUseCase.delete(request.pathVariable("id"))
                        .flatMap(dogDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Dog Deleted"))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> updateDog(UpdateDogWalkerUseCase  updateDogWalkerUseCase){
        return route(PUT("/dogWalker/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogWalkerDTO.class)
                        .flatMap(dogWalkerDTO -> updateDogWalkerUseCase.update(request.pathVariable("id"), dogWalkerDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }
}
