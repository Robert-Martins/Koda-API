package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobReadDto {

    private int id;

    private String name;

    private String description;

    private int commentCount;

    private Date updatedAt;

    private Date createdAt;

}
