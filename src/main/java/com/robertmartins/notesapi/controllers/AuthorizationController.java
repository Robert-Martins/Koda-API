package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizationController {

    @Autowired
    private UserService userService;

    public boolean itIsUserWorkspace(int id, WorkspaceModel workspaceModel){
        var user = userService.findById(id);
        var workspaceList = user.get().getWorkspaces();
        if(workspaceList.contains(workspaceModel))
            return true;
        return false;
    }

    public boolean itIsUserAddress(int id, int addressId){
        var user = userService.findById(id);
        var userAddressId = user.get().getProfile().getAddress().getId();
        if(userAddressId == addressId)
            return true;
        return false;
    }

}
