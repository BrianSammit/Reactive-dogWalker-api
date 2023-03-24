package com.brian.springreactivedogwalker.domain.collection;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "dog-walkers")
public class DogWalker {
    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="lastname is required")
    private String lastname;
    @Positive
    @Max(value = 100, message = "Age should not be greater than 100")
    private Integer age;
}
