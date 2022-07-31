package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.resources.CommentResource;
import com.robertmartins.notesapi.resources.JobResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/jobs/{jobId}/comments")
public class CommentController {

    @Autowired
    private CommentResource commentResource;

    @Autowired
    private JobResource jobResource;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id ,@PathVariable(name = "jobId") int jobId, @RequestBody @Valid CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResource.save(commentDto, id, jobId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "commentId") int commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentResource.findById(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> updateById(@PathVariable(name = "commentId") int commentId, @RequestBody @Valid CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.OK).body(commentResource.update(commentDto, commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteById(@PathVariable(name = "commentId") int commentId){
        commentResource.delete(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("Comment Deleted");
    }

    @GetMapping
    public ResponseEntity<Object> findAllCommentsInAJob(@PathVariable(name = "jobId") int jobId){
        return ResponseEntity.status(HttpStatus.OK).body(jobResource.findById(jobId).getComments());
    }

}
