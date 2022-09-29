package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.dtos.CommentReadDto;
import com.robertmartins.notesapi.models.CommentModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentResource {

    CommentModel save(CommentDto commentDto, int userId , int jobId);

    CommentModel update(CommentDto commentDto, int commentId);

    CommentModel findById(int id);

    CommentReadDto getById(int id);

    void deleteById(int id);

    List<CommentReadDto> getAllCommentsInAJob(int jobId);

    boolean commentExists(int id);

}
