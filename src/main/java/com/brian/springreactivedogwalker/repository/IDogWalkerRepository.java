package com.brian.springreactivedogwalker.repository;

import com.brian.springreactivedogwalker.domain.collection.DogWalker;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDogWalkerRepository extends ReactiveMongoRepository<DogWalker, String> {
}
