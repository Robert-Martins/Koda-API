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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/workspace/{workspaceId}/status")
public class JobStatusController {

    @Autowired
    private JobStatusResource jobStatusResource;

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<ClientResponseDto> save(Authentication authentication, @PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobStatusDto jobStatusDto){
        if(
                !authorizationResource.checkJwtAuthorization(id, authentication.getName()) ||
                !authorizationResource.itIsUserWorkspace(id, workspaceId)
        )
            throw new ActionNotAllowedException();
        var jobStatus = jobStatusResource.save(jobStatusDto, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(jobStatus.getId())
                        .operationType("CREATE")
                        .status(HttpStatus.CREATED.value())
                        .message("Status Registered Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<JobStatusModel> getJobStatus(Authentication authentication, @PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.OK).body(jobStatusResource.findById(statusId));
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<ClientResponseDto> updateStatusById(Authentication authentication, @PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId, @RequestBody @Valid JobStatusDto jobStatusDto){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(
                !authorizationResource.itIsUserWorkspace(id, workspaceId) ||
                !authorizationResource.itIsWorkspaceStatus(workspaceId, statusId)
        )
            throw new ActionNotAllowedException();
        var jobStatus = jobStatusResource.update(jobStatusDto, statusId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(jobStatus.getId())
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("Status Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{statusId}/position")
    public ResponseEntity<ClientResponseDto> updateStatusPositionById(Authentication authentication, @PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId, @RequestParam String position){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(
                !authorizationResource.itIsUserWorkspace(id, workspaceId) ||
                !authorizationResource.itIsWorkspaceStatus(workspaceId, statusId)
        )
            throw new ActionNotAllowedException();
        var jobStatus = jobStatusResource.changePosition(statusId, workspaceId, Integer.parseInt(position));
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(jobStatus.getId())
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("Status Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<ClientResponseDto> deleteStatusById(Authentication authentication, @PathVariable(name = "id")int id, @PathVariable(name = "workspaceId") int workspaceId, @PathVariable(name = "statusId") int statusId){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        if(!jobStatusResource.jobStatusExists(statusId))
            throw new ResourceNotFoundException("Status Not Found");
        if(
                !authorizationResource.itIsUserWorkspace(id, workspaceId) ||
                !authorizationResource.itIsWorkspaceStatus(workspaceId, statusId)
        )
            throw new ActionNotAllowedException();
        workspaceResource.deleteStatusById(workspaceId, statusId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .operationType("DELETE")
                        .status(HttpStatus.OK.value())
                        .message("Status Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
