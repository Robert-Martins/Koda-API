package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/workspace/{workspaceId}/jobs")
public class JobController {

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private JobResource jobResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<ClientResponseDto> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobDto jobDto){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        var job = jobResource.save(jobDto, id, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(job.getId())
                        .operationType("CREATE")
                        .status(HttpStatus.CREATED.value())
                        .message("Job Registered Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobModel> getJobById(@PathVariable(name = "jobId") int jobId){
        return ResponseEntity.status(HttpStatus.OK).body(jobResource.findById(jobId));
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<ClientResponseDto> updateJobById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "jobId") int jobId, @RequestBody @Valid JobDto jobDto){
        if(!jobResource.jobExists(jobId))
            throw new ResourceNotFoundException("Job Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceJob(workspaceId, jobId))
            throw new ActionNotAllowedException();
        var job = jobResource.update(jobDto, jobId);
        return ResponseEntity.status(HttpStatus.OK).body(ClientResponseDto.builder()
                .id(job.getId())
                .operationType("CREATE")
                .status(HttpStatus.OK.value())
                .message("Job Updated Successfully")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<ClientResponseDto> deleteJobById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "jobId") int jobId){
        if(!jobResource.jobExists(jobId))
            throw new ResourceNotFoundException("Job Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceJob(workspaceId, jobId))
            throw new ActionNotAllowedException();
        workspaceResource.deleteJobById(workspaceId, jobId);
        return ResponseEntity.status(HttpStatus.OK).body(ClientResponseDto.builder()
                .operationType("DELETE")
                .status(HttpStatus.OK.value())
                .message("Job Deleted")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

}
