package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.services.JobStatusService;
import com.robertmartins.notesapi.services.WorkspaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/status")
public class JobStatusController {

    @Autowired
    private JobStatusService jobStatusService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private AuthorizationController authorizationController;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable(name = "id") int id, @PathVariable(name = "workspaceId") int workspaceId, @RequestBody @Valid JobStatusDto jobStatusDto){
        var workspace = workspaceService.findById(workspaceId);
        if(workspace.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace Not Found");
        if(!authorizationController.itIsUserWorkspace(id, workspace.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        var jobStatusList = workspace.get().getJobStatus();
        var jobStatus = new JobStatusModel();
        BeanUtils.copyProperties(jobStatusDto, jobStatus);
        jobStatus.setUpdatedAt(new Date());
        jobStatus.setCreatedAt(new Date());
        jobStatusList.add(jobStatus);
        workspace.get().setJobStatus(jobStatusList);
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceService.save(workspace.get()));
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<Object> getJobStatus(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        var jobStatus = jobStatusService.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationController.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.OK).body(jobStatus.get());
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<Object> updateStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId, JobStatusDto jobStatusDto){
        var jobStatus = jobStatusService.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationController.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        BeanUtils.copyProperties(jobStatusDto, jobStatus);
        jobStatus.get().setUpdatedAt(new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(jobStatusService.save(jobStatus.get()));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Object> deleteStatusById(@PathVariable(name = "id")int id, @PathVariable(name = "statusId") int statusId){
        var jobStatus = jobStatusService.findById(statusId);
        if(jobStatus.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status Not Found");
        if(!authorizationController.itIsUserWorkspaceStatus(id, statusId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        jobStatusService.deleteById(statusId);
        return ResponseEntity.status(HttpStatus.OK).body("Status Deleted");
    }



    public List<JobStatusModel> createGenericStatus(){
        List<JobStatusModel> jobStatusList = new ArrayList<>();
        jobStatusList.add(new JobStatusModel("To-Do", "Jobs to be done", "##ff1c37", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Development", "Jobs in development", "##ffc800", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Done", "Jobs done", "##29e000", new Date(), new Date()));
        return jobStatusList;
    }

}
