package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.services.UserService;
import com.robertmartins.notesapi.services.WorkspaceService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobStatusController jobStatusController;

    @Autowired
    private AuthorizationController authorizationController;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid NewWorkspaceDto newWorkspaceDto, @PathVariable(name = "id") int id){
        var user = userService.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        List<WorkspaceModel> workspaceModelList = user.get().getWorkspaces();
        workspaceModelList.add(this.setWorkspace(newWorkspaceDto));
        user.get().setWorkspaces(workspaceModelList);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<Object> findUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        var workspace = workspaceService.findById(workspaceId);
        if(!authorizationController.itIsUserWorkspace(id, workspace.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(workspace.get());
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Object> updateUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid NewWorkspaceDto workspaceDto){
        var workspace = workspaceService.findById(workspaceId);
        if(!authorizationController.itIsUserWorkspace(id, workspace.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        workspace.get().setName(workspaceDto.getName());
        workspace.get().setDescription(workspaceDto.getDescription());
        workspace.get().setJobStatus(workspaceDto.getJobStatusModelList());
        workspace.get().setUpdatedAt(new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceService.save(workspace.get()));
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Object> deleteUserWorkspaceById(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId){
        var workspace = workspaceService.findById(workspaceId);
        if(!authorizationController.itIsUserWorkspace(id, workspace.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        workspaceService.deleteById(workspaceId);
        return ResponseEntity.status(HttpStatus.OK).body("Workspace Deleted");
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserWorkspaces(@PathVariable(name = "id") int id){
        var user = userService.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(user.get().getWorkspaces());
    }

    public WorkspaceModel setWorkspace(NewWorkspaceDto newWorkspaceDto){
        var workspace = new WorkspaceModel();
        workspace.setName(newWorkspaceDto.getName());
        workspace.setDescription(newWorkspaceDto.getDescription());
        workspace.setUpdatedAt(new Date());
        if(workspace.getCreatedAt() == null){
            workspace.setJobStatus(jobStatusController.createGenericStatus());
            workspace.setCreatedAt(new Date());
        }
        return workspace;
    }

}
