package com.robertmartins.notesapi.resources;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationResource {

    boolean itIsUserWorkspace(int id, int workspaceId);

    boolean itIsUserWorkspaceStatus(int id, int statusId);

    boolean itIsUserAddress(int id, int addressId);

}
