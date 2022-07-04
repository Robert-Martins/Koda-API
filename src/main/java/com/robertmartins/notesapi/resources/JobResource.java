package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;

import java.util.Optional;

public interface JobResource {

    JobModel save(JobDto jobDto, int workspaceId);

    Optional<JobModel> findById(int id);

}
