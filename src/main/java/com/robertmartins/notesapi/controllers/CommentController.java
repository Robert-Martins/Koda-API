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
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id ,@PathVariable(name = "jobId") int jobId, CommentDto commentDto){
        var job = jobResource.findById(jobId);
        if(job.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Not Found");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResource.save(commentDto, id, jobId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "commentId") int id){
        var comment = commentResource.findById(id);
        if(comment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> updateById(@PathVariable(name = "commentId") int id, @RequestBody @Valid CommentDto commentDto){
        var comment = commentResource.findById(id);
        if(comment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(commentResource.update(commentDto, id));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteById(@PathVariable(name = "commentId") int id){
        var comment = commentResource.findById(id);
        if(comment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        commentResource.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Comment Deleted");
    }

    @GetMapping
    public ResponseEntity<Object> findAllCommentsInAJob(@PathVariable(name = "jobId") int jobId){
        var job = jobResource.findById(jobId);
        if(job.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(job.get().getComments());
    }

}
