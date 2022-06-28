package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    private ProfileDto profile;

    @NotNull
    @Size(max = 30, min = 8)
    private String password;

}
