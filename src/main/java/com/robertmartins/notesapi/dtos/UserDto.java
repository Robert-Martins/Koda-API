package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    private ProfileDto profile;

    private UserDeviceDto device;

    @NotEmpty
    @Size(max = 30, min = 8)
    private String password;

}
