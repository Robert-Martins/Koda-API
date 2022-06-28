package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public WorkspaceModel save(WorkspaceModel workspaceModel){
        return workspaceRepository.save(workspaceModel);
    }

    public void deleteById(int id){
        workspaceRepository.deleteById(id);
    }

    public Optional<WorkspaceModel> findById(int id){
        return workspaceRepository.findById(id);
    }

}
