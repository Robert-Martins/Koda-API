package com.robertmartins.notesapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserReadDto {

    private int id;

    private String name;

}
