package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService implements AuthorizationResource {

    @Autowired
    private UserResource userResource;

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private JobResource jobResource;

    public boolean itIsUserAddress(int id, int addressId){
        var user = userResource.findById(id);
        var userAddressId = user.getProfile().getAddress().getId();
        return userAddressId == addressId;
    }

    public boolean itIsUserWorkspace(int id, int workspaceId){
        var user = userResource.findById(id);
        var workspaceList = user.getWorkspaces();
        return workspaceList.stream()
                .anyMatch(workspace -> workspace.getId() == workspaceId);
    }

    public boolean itIsWorkspaceStatus(int workspaceId, int jobStatusId){
        var workspace = workspaceResource.findById(workspaceId);
        var jobStatusList = workspace.getJobStatus();
        return jobStatusList.stream()
                .anyMatch(jobStatus -> jobStatus.getId() == jobStatusId);
    }

    public boolean itIsWorkspaceJob(int workspaceId, int jobId){
        var workspace = workspaceResource.findById(workspaceId);
        var jobsList = workspace.getJobs();
        return jobsList.stream()
                .anyMatch(jobStatus -> jobStatus.getId() == jobId);
    }

    public boolean itIsJobComment(int jobId, int commentId) {
        var job = jobResource.findById(jobId);
        var commentsList = job.getComments();
        return commentsList.stream()
                .anyMatch(comment -> comment.getId() == commentId);
    }

}
