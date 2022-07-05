package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.JobResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/jobs")
public class JobController {

    @Autowired
    private JobResource jobResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobDto jobDto){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResource.save(jobDto, workspaceId));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Object> getJobById(@PathVariable(name = "jobId") int id){
        var job = jobResource.findById(id);
        if(job.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(job.get());
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Object> updateJobById(@PathVariable(name = "jobId") int id, @RequestBody @Valid JobDto jobDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResource.update(jobDto, id));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Object> deleteJobById(@PathVariable(name = "jobId") int id){
        jobResource.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }

}
