package com.brian.springreactivedogwalker.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteDogWalker {
    Mono<Void> delete(String id);
}
