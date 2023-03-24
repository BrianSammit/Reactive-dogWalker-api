package com.brian.springreactivedogwalker.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DogDTO {
    private String id;
    private String name;
    private String breed;
    private Integer age;
    private String color;
    private Boolean isAdded;
}
