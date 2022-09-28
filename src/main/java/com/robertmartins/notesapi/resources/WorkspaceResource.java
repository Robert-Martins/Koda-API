package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import org.springframework.stereotype.Service;

@Service
public interface WorkspaceResource {

    WorkspaceModel save(NewWorkspaceDto workspaceDto, int id);

    WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId);

    WorkspaceModel findById(int id);

    void deleteById(int workspaceId, int id);

    void deleteStatusById(int workspaceId, int id);

    boolean workspaceExists(int id);

}
