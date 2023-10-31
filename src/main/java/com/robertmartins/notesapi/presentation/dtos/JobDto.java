package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JobDto {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    private com.robertmartins.notesapi.models.User creatorAndAssign;

    @NotNull
    private int statusId;

}