package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;

import java.util.Optional;

public interface WorkspaceResource {

    WorkspaceModel save(NewWorkspaceDto workspaceDto, int id);

    WorkspaceModel saveWorkspace(WorkspaceModel workspace);

    WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId);

    Optional<WorkspaceModel> findById(int id);

    void deleteById(int id);

}
