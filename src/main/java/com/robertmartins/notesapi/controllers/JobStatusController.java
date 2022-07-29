package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/status")
public class JobStatusController {

    @Autowired
    private JobStatusResource jobStatusResource;

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobStatusDto jobStatusDto){
        var workspace = workspaceResource.findById(workspaceId);
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusResource.save(jobStatusDto, workspaceId));
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<Object> getJobStatus(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        var jobStatus = jobStatusResource.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationResource.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.OK).body(jobStatus.get());
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<Object> updateStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId, @RequestBody @Valid JobStatusDto jobStatusDto){
        var jobStatus = jobStatusResource.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationResource.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusResource.update(jobStatusDto, statusId));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Object> deleteStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        var jobStatus = jobStatusResource.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationResource.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        jobStatusResource.deleteById(statusId);
        return ResponseEntity.status(HttpStatus.OK).body("Status Deleted");
    }

}
