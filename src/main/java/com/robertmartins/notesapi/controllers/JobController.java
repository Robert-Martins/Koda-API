package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.services.JobService;
import com.robertmartins.notesapi.services.WorkspaceService;
import org.springframework.beans.BeanUtils;
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
    private AuthorizationController authorizationController;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobDto jobDto){
        if(!authorizationController.itIsUserWorkspace(id, workspaceId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResource.save(jobDto, workspaceId));
    }


}
