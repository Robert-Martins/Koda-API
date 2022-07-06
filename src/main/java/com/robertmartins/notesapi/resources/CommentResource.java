package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.models.CommentModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CommentResource {

    CommentModel save(CommentDto commentDto, int userId , int jobId);

    CommentModel update(CommentDto commentDto, int commentId);

    void delete(int id);

    Optional<CommentModel> findById(int id);

}
