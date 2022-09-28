package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
        List<WorkspaceModel> workspaceModelList = user.getWorkspaces();
        workspaceModelList.add(workspace);
        user.setWorkspaces(workspaceModelList);
        userResource.saveUser(user);
        return workspaceModelList.get(workspaceModelList.size() - 1);
    }

    public WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId){
        var workspace = this.findById(workspaceId);
        workspace.setName(workspaceDto.getName());
        workspace.setDescription(workspaceDto.getDescription());
        workspace.setUpdatedAt(new Date());
        return workspaceRepository.save(workspace);
    }

    public void deleteById(int workspaceId, int id){
        var user = userResource.findById(id);
        user.getWorkspaces().remove(this.findById(workspaceId));
        userResource.saveUser(user);
    }

    public WorkspaceModel findById(int id){
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace Not Found"));
    }

    public void deleteStatusById(int workspaceId, int id){
        var workspace = this.findById(workspaceId);
        workspace.getJobStatus().remove(jobStatusResource.findById(id));
        workspaceRepository.save(workspace);
    }

    public boolean workspaceExists(int id){
        return workspaceRepository.existsById(id);
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
