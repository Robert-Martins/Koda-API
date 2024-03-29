package com.robertmartins.notesapi.dtos;

import com.robertmartins.notesapi.models.AddressModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserProfileDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 30)
    private String email;

    @Size(max = 15)
    private String cpf;

    @Size(max = 15)
    private String birthDate;

    @Size(max = 15)
    private String telephone;

    private AddressDto address;

}