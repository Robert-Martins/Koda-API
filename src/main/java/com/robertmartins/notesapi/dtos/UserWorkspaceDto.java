package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserWorkspaceDto {

    @NotNull
    private int id;

    @NotNull
    private NewWorkspaceDto workspace;

}
