package com.robertmartins.notesapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class JobStatusDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private String color;

}
