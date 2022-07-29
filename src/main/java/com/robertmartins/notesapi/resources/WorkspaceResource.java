package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WorkspaceResource {

    WorkspaceModel save(NewWorkspaceDto workspaceDto, int id);

    WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId);

    Optional<WorkspaceModel> findById(int id);

    void deleteById(int id);

}
