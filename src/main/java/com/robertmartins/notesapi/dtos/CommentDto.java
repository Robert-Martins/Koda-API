package com.robertmartins.notesapi.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentDto {

    @NotNull
    @Size(max = 255)
    private String comment;

    @NotNull
    private int userId;

    @NotNull
    private int jobId;

}
