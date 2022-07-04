package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.CommentDto;
import com.robertmartins.notesapi.models.CommentModel;
import com.robertmartins.notesapi.resources.CommentResource;
import com.robertmartins.notesapi.resources.JobResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
