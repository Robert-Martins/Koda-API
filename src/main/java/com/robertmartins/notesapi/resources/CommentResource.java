package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.models.CommentModel;

import java.util.Optional;

public interface CommentResource {

    CommentModel save(CommentDto commentDto, int userId , int jobId);

    CommentModel update(CommentDto commentDto, int commentId);

    void delete(int id);

    Optional<CommentModel> findById(int id);

}
