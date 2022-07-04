package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.models.CommentModel;

public interface CommentResource {

    CommentModel save(CommentDto commentDto, int userId , int jobId);

}
