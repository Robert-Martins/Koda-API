package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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
    public ResponseEntity<JobStatusModel> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobStatusDto jobStatusDto){
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusResource.save(jobStatusDto, workspaceId));
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<JobStatusModel> getJobStatus(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        return ResponseEntity.status(HttpStatus.OK).body(jobStatusResource.findById(statusId));
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<JobStatusModel> updateStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId, @RequestBody @Valid JobStatusDto jobStatusDto){
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceStatus(workspaceId, statusId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusResource.update(jobStatusDto, statusId));
    }

    @PutMapping("/{statusId}/position")
    public ResponseEntity<JobStatusModel> updateStatusPositionById(@PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId, @RequestParam String position){
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceStatus(workspaceId, statusId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusResource.changePosition(statusId, workspaceId, Integer.parseInt(position)));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<ClientResponseDto> deleteStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId){
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        if(!authorizationResource.itIsWorkspaceStatus(workspaceId, statusId))
            throw new ActionNotAllowedException();
        workspaceResource.deleteStatusById(workspaceId, statusId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .message("Status Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
