package com.robertmartins.notesapi.dtos;

import com.robertmartins.notesapi.enums.UfEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class AddressDto {

    @Size(max = 20)
    private String country;

    @Size(max = 15)
    private String postalCode;

    private UfEnum uf;

    @Size(max = 20)
    private String city;

}
