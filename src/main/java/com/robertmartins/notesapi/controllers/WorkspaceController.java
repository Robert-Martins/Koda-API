package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.UserWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.services.UserService;
import com.robertmartins.notesapi.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user/{id}/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobStatusController jobStatusController;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid NewWorkspaceDto newWorkspaceDto, @PathVariable(name = "id") int id){
        var user = userService.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        List<WorkspaceModel> workspaceModelList = user.get().getWorkspaceModels();
        workspaceModelList.add(this.setWorkspace(newWorkspaceDto));
        user.get().setWorkspaceModels(workspaceModelList);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    public WorkspaceModel setWorkspace(NewWorkspaceDto newWorkspaceDto){
        var newWorkspace = new WorkspaceModel();
        newWorkspace.setName(newWorkspaceDto.getName());
        newWorkspace.setDescription(newWorkspaceDto.getDescription());
        newWorkspace.setJobStatusModels(jobStatusController.createGenericStatus());
        newWorkspace.setUpdatedAt(new Date());
        newWorkspace.setCreatedAt(new Date());
        return newWorkspace;
    }

}
