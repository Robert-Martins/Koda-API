package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class WorkspaceReadDto {

    private int id;

    private String name;

    private String description;

    private int jobCount;

    private List<JobStatusReadDto> status;

    private Date updatedAt;

    private Date createdAt;

}
