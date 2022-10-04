package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JobStatusReadDto {

    private int id;

    private String name;

    private String description;

    private int statusJobCount;

    private List<JobReadDto> jobs;

    private int position;

    private Date updatedAt;

    private Date createdAt;

}
