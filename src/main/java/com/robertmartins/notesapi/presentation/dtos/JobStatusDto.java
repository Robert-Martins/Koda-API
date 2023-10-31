package com.robertmartins.notesapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class JobStatusDto {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @NotEmpty
    private String color;

}
