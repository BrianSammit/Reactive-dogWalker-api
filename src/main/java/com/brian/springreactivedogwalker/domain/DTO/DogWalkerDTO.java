package com.brian.springreactivedogwalker.domain.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogWalkerDTO {
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
    private List<DogDTO> dogsGroup;

}
