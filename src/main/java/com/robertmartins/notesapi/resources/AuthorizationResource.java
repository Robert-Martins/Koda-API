package com.robertmartins.notesapi.resources;

public interface AuthorizationResource {

    boolean itIsUserWorkspace(int id, int workspaceId);

    boolean itIsUserWorkspaceStatus(int id, int statusId);

    boolean itIsUserAddress(int id, int addressId);

}
