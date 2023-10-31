package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobStatusResource {

    com.robertmartins.notesapi.models.JobStatus save(JobStatusDto jobStatusDto, int id);

    com.robertmartins.notesapi.models.JobStatus update(JobStatusDto jobStatusDto, int id);

    com.robertmartins.notesapi.models.JobStatus findById(int id);

    com.robertmartins.notesapi.models.JobStatus changePosition(int id, int workspaceId, int position);

    void organizePositions(int workspaceId);

    List<com.robertmartins.notesapi.models.JobStatus> createGenericStatus();

    boolean jobStatusExists(int id);

}
