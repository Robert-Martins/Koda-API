package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.dtos.CommentReadDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentResource {

    com.robertmartins.notesapi.models.Comment save(CommentDto commentDto, int userId , int jobId);

    com.robertmartins.notesapi.models.Comment update(CommentDto commentDto, int commentId);

    com.robertmartins.notesapi.models.Comment findById(int id);

    CommentReadDto getById(int id);

    void deleteById(int id);

    List<CommentReadDto> getAllCommentsInAJob(int jobId);

    boolean commentExists(int id);

}
