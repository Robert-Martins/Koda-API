package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;
import org.springframework.stereotype.Service;

@Service
public interface JobResource {

    JobModel save(JobDto jobDto, int id, int workspaceId);

    JobModel update(JobDto jobDto, int jobId);

    JobModel findById(int id);

    boolean jobExists(int id);

}
