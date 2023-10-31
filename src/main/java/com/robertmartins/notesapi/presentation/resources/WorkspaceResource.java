package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.WorkspaceListDto;
import com.robertmartins.notesapi.dtos.WorkspaceReadDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkspaceResource {

    com.robertmartins.notesapi.models.Workspace save(NewWorkspaceDto workspaceDto, int id);

    com.robertmartins.notesapi.models.Workspace update(NewWorkspaceDto workspaceDto, int workspaceId);

    com.robertmartins.notesapi.models.Workspace findById(int id);

    WorkspaceReadDto getWorkspace(int id);

    List<WorkspaceListDto> findAll(int id);

    void deleteById(int workspaceId);

    void deleteJobById(int id);
    void deleteStatusById(int workspaceId, int id);

    boolean workspaceExists(int id);

}
