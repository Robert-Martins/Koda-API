package com.robertmartins.notesapi.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CommentReadDto {

    private int id;

    private String comment;

    private UserReadDto user;

    private Date updateAt;

    private Date createdAt;

}
