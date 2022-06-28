package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

}
