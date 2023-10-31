package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobDto;
import org.springframework.stereotype.Service;

@Service
public interface JobResource {

    com.robertmartins.notesapi.models.Job save(JobDto jobDto, int id, int workspaceId);

    com.robertmartins.notesapi.models.Job update(JobDto jobDto, int jobId);

    com.robertmartins.notesapi.models.Job findById(int id);

    boolean jobExists(int id);

}
