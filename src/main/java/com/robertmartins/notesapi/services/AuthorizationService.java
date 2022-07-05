package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizationService implements AuthorizationResource {

    @Autowired
    private UserResource userResource;

    public boolean itIsUserWorkspace(int id, int workspaceId){
        var user = userResource.findById(id);
        var workspaceList = user.get().getWorkspaces();
        for(WorkspaceModel workspace : workspaceList){
            if(workspace.getId() == workspaceId)
                return true;
        }
        return false;
    }

    public boolean itIsUserWorkspaceStatus(int id, int statusId){
        var user = userResource.findById(id);
        var workspaceList = user.get().getWorkspaces();
        for(WorkspaceModel workspace : workspaceList){
            var statusList = workspace.getJobStatus();
            for(JobStatusModel status : statusList){
                if(status.getId() == statusId)
                    return true;
            }
        }
        return false;
    }

    public boolean itIsUserAddress(int id, int addressId){
        var user = userResource.findById(id);
        var userAddressId = user.get().getProfile().getAddress().getId();
        if(userAddressId == addressId)
            return true;
        return false;
    }

}
