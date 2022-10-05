package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.PaginatedResponseDto;
import com.robertmartins.notesapi.dtos.WorkspaceReadDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
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
    public ResponseEntity<ClientResponseDto> save(@RequestBody @Valid NewWorkspaceDto newWorkspaceDto, @PathVariable(name = "id") int id){
        var workspace = workspaceResource.save(newWorkspaceDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(workspace.getId())
                        .operationType("CREATE")
                        .status(HttpStatus.CREATED.value())
                        .message("Workspace Registered Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceReadDto> findUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        return ResponseEntity.status(HttpStatus.OK).body(workspaceResource.getWorkspace(workspaceId));
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<ClientResponseDto> updateUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid NewWorkspaceDto workspaceDto){
        if(!workspaceResource.workspaceExists(workspaceId))
            throw new ResourceNotFoundException("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        var workspace = workspaceResource.update(workspaceDto, workspaceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(workspace.getId())
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("Workspace Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ClientResponseDto> deleteUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        if(!workspaceResource.workspaceExists(workspaceId))
            throw new ResourceNotFoundException("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            throw new ActionNotAllowedException();
        workspaceResource.deleteById(workspaceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .operationType("DELETE")
                        .status(HttpStatus.OK.value())
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
