package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDeviceDto {

    @NotEmpty
    private String ip;

    @NotEmpty
    private String os;

    @NotEmpty
    private String browser;

    @NotEmpty
    private String appVersion;

    @NotEmpty
    private String browserLanguage;

}
