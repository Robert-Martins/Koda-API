package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.controllers.JobStatusController;
import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class WorkspaceService implements WorkspaceResource {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private JobStatusResource jobStatusResource;

    public WorkspaceModel save(NewWorkspaceDto workspaceDto, int id){
        var user = userResource.findById(id);
        var workspace = this.setWorkspace(workspaceDto);
        List<WorkspaceModel> workspaceModelList = user.get().getWorkspaces();
        workspaceModelList.add(workspace);
        user.get().setWorkspaces(workspaceModelList);
        userResource.saveUser(user.get());
        return workspace;
    }

    public WorkspaceModel saveWorkspace(WorkspaceModel workspace){
        return workspaceRepository.save(workspace);
    }

    public WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId){
        var workspace = this.findById(workspaceId);
        workspace.get().setName(workspaceDto.getName());
        workspace.get().setDescription(workspaceDto.getDescription());
        workspace.get().setUpdatedAt(new Date());
        return workspaceRepository.save(workspace.get());
    }

    public void deleteById(int id){
        workspaceRepository.deleteById(id);
    }

    public Optional<WorkspaceModel> findById(int id){
        return workspaceRepository.findById(id);
    }

    public WorkspaceModel setWorkspace(NewWorkspaceDto newWorkspaceDto){
        var workspace = new WorkspaceModel();
        workspace.setName(newWorkspaceDto.getName());
        workspace.setDescription(newWorkspaceDto.getDescription());
        workspace.setUpdatedAt(new Date());
        if(workspace.getCreatedAt() == null){
            workspace.setJobStatus(jobStatusResource.createGenericStatus());
            workspace.setCreatedAt(new Date());
        }
        return workspace;
    }

}
