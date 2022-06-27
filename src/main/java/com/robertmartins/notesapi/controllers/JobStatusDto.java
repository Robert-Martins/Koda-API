package com.robertmartins.notesapi.controllers;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JobStatusDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private String color;

}
