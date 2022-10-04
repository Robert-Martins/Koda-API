package com.robertmartins.notesapi.dtos;

import com.robertmartins.notesapi.models.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JobDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    private UserModel creatorAndAssign;

    @NotNull
    private int statusId;

}