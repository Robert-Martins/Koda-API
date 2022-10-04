package com.robertmartins.notesapi.dtos;

import com.robertmartins.notesapi.models.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JobDto {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    private UserModel creatorAndAssign;

    @NotEmpty
    private int statusId;

}