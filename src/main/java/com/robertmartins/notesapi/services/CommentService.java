package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.models.CommentModel;
import com.robertmartins.notesapi.repositories.CommentRepository;
import com.robertmartins.notesapi.resources.CommentResource;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CommentService implements CommentResource{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private JobResource jobResource;

    public CommentModel save(CommentDto commentDto, int userId ,int jobId){
        var comment = setComment(commentDto, userId, jobId);
        return commentRepository.save(comment);
    }

    private CommentModel setComment(CommentDto commentDto, int userId, int jobId){
        var user = userResource.findById(userId);
        var job = jobResource.findById(jobId);
        var comment = new CommentModel();
        comment.setComment(commentDto.getComment());
        comment.setUser(user.get());
        comment.setJob(job.get());
        comment.setUpdatedAt(new Date());
        comment.setCreatedAt(new Date());
        return comment;
    }

}
