package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.dtos.DeletedResourceDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.models.CommentModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.CommentResource;
import com.robertmartins.notesapi.resources.JobResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/jobs/{jobId}/comments")
public class CommentController {

    @Autowired
    private CommentResource commentResource;

    @Autowired
    private JobResource jobResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<CommentModel> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId ,@PathVariable(name = "jobId") int jobId, @RequestBody @Valid CommentDto commentDto){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceJob(workspaceId, jobId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResource.save(commentDto, id, jobId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentModel> getById(@PathVariable(name = "commentId") int commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentResource.findById(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentModel> updateById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId , @PathVariable(name = "jobId") int jobId, @PathVariable(name = "commentId") int commentId, @RequestBody @Valid CommentDto commentDto){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceJob(workspaceId, jobId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsJobComment(jobId, commentId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.OK).body(commentResource.update(commentDto, commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeletedResourceDto> deleteById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId ,@PathVariable(name = "jobId") int jobId, @PathVariable(name = "commentId") int commentId){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceJob(workspaceId, jobId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsJobComment(jobId, commentId))
            throw new ActionNotAllowedException();
        commentResource.delete(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                DeletedResourceDto.builder()
                        .message("Comment Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Object> findAllCommentsInAJob(@PathVariable(name = "jobId") int jobId){
        return ResponseEntity.status(HttpStatus.OK).body(jobResource.findById(jobId).getComments());
    }

}
