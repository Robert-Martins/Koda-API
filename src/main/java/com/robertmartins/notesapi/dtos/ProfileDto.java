package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProfileDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 15)
    private String cpf;

    @Size(max = 15)
    private String birthDate;

    @Size(max = 15)
    private String telephone;

    @NotNull
    @Size(max = 30)
    private String email;

    @NotNull
    @Size(max = 30, min = 8)
    private String password;

}
