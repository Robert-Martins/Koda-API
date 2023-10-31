package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WorkspaceListDto {

    private int id;

    private String name;

    private String description;

    private int jobCount;

    private Date updatedAt;

    private Date createdAt;

}
