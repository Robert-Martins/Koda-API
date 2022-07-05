package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Object> save(@RequestBody @Valid NewWorkspaceDto newWorkspaceDto, @PathVariable(name = "id") int id){
        var user = userResource.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceResource.save(newWorkspaceDto, id));
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<Object> findUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        var workspace = workspaceResource.findById(workspaceId);
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(workspace.get());
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Object> updateUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid NewWorkspaceDto workspaceDto){
        var workspace = workspaceResource.findById(workspaceId);
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceResource.update(workspaceDto, workspaceId));
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Object> deleteUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        var workspace = workspaceResource.findById(workspaceId);
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        if(!authorizationResource.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        workspaceResource.deleteById(workspaceId);
        return ResponseEntity.status(HttpStatus.OK).body("Workspace Deleted");
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserWorkspaces(@PathVariable(name = "id") int id){
        var user = userResource.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(user.get().getWorkspaces());
    }


}
