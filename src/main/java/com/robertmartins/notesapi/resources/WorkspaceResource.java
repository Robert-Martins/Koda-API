package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.WorkspaceListDto;
import com.robertmartins.notesapi.dtos.WorkspaceReadDto;
import com.robertmartins.notesapi.models.WorkspaceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkspaceResource {

    WorkspaceModel save(NewWorkspaceDto workspaceDto, int id);

    WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId);

    WorkspaceModel findById(int id);

    WorkspaceReadDto getWorkspace(int id);

    List<WorkspaceListDto> findAll(int id);

    void deleteById(int workspaceId);

    void deleteJobById(int id);
    void deleteStatusById(int workspaceId, int id);

    boolean workspaceExists(int id);

}
