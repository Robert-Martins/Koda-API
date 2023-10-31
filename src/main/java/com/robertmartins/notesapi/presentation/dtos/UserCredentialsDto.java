package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCredentialsDto {

    @NotNull
    @Size(max = 30)
    private String login;

    @NotNull
    @Size(max = 30, min = 8)
    private String password;

}