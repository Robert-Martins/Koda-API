package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.PaginatedResponseDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private UserResource userResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping
    public ResponseEntity<WorkspaceModel> save(@RequestBody @Valid NewWorkspaceDto newWorkspaceDto, @PathVariable(name = "id") int id){
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceResource.save(newWorkspaceDto, id));
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceModel> findUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        return ResponseEntity.status(HttpStatus.OK).body(workspaceResource.findById(workspaceId));
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceModel> updateUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid NewWorkspaceDto workspaceDto){
        if(!workspaceResource.workspaceExists(workspaceId))
            throw new ResourceNotFoundException("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceResource.update(workspaceDto, workspaceId));
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ClientResponseDto> deleteUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        if(!workspaceResource.workspaceExists(workspaceId))
            throw new ResourceNotFoundException("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        workspaceResource.deleteById(workspaceId, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .message("Workspace Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto> getAllUserWorkspaces(@PathVariable(name = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(PaginatedResponseDto.builder()
                .content(workspaceResource.findAll(id))
                .itemsPerPage(0)
                .page(0)
                .order("")
                .orderedBy("")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }


}
