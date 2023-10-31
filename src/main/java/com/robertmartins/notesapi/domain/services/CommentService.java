package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.dtos.CommentReadDto;
import com.robertmartins.notesapi.dtos.UserReadDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.repositories.CommentRepository;
import com.robertmartins.notesapi.resources.CommentResource;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CommentService implements CommentResource{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private JobResource jobResource;

    public com.robertmartins.notesapi.models.Comment save(CommentDto commentDto, int userId , int jobId){
        var comment = setComment(commentDto, userId, jobId);
        return commentRepository.save(comment);
    }

    @Transactional
    public com.robertmartins.notesapi.models.Comment update(CommentDto commentDto, int commentId){
        var comment = this.findById(commentId);
        comment.setComment(commentDto.getComment());
        comment.setUpdatedAt(new Date());
        return commentRepository.save(comment);
    }

    public com.robertmartins.notesapi.models.Comment findById(int id) throws ResourceNotFoundException{
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment Not Found"));
    }

    public CommentReadDto getById(int id) throws ResourceNotFoundException{
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment Not Found"));
        return this.normalizeComment(comment);
    }

    @Transactional
    public void deleteById(int id){
        commentRepository.deleteCommentById(id);
    }

    public List<CommentReadDto> getAllCommentsInAJob(int id){
        var comments = jobResource.findById(id).getComments();
        List<CommentReadDto> readables = new ArrayList<>();
        comments.stream().forEach(comment -> readables.add(this.normalizeComment(comment)));
        return readables;
    }

    private com.robertmartins.notesapi.models.Comment setComment(CommentDto commentDto, int userId, int jobId){
        var user = userResource.findById(userId);
        var job = jobResource.findById(jobId);
        var comment = new com.robertmartins.notesapi.models.Comment();
        comment.setComment(commentDto.getComment());
        comment.setUser(user);
        comment.setJob(job);
        comment.setUpdatedAt(new Date());
        comment.setCreatedAt(new Date());
        return comment;
    }

    private CommentReadDto normalizeComment(com.robertmartins.notesapi.models.Comment comment){
        var user = comment.getUser();
        return CommentReadDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .user(new UserReadDto(user.getId(), user.getProfile().getName()))
                .createdAt(comment.getCreatedAt())
                .updateAt(comment.getUpdatedAt())
                .build();
    }

    public boolean commentExists(int id){
        return commentRepository.existsById(id);
    }

}
