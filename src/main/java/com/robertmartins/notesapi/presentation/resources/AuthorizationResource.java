package com.robertmartins.notesapi.resources;

import org.springframework.stereotype.Service;

@Service
public interface AuthorizationResource {

    boolean itIsUserWorkspace(int id, int workspaceId);

    boolean itIsWorkspaceStatus(int workspaceId, int jobStatusId);

    boolean itIsWorkspaceJob(int workspaceId, int jobId);

    boolean itIsJobComment(int jobId, int commentId);

    boolean itIsUserAddress(int id, int addressId);

    boolean itIsUserDevice(int id, int deviceId);

    boolean checkJwtAuthorization(int id, String username);

}
