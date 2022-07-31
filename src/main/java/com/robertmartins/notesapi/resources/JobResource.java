package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JobResource {

    JobModel save(JobDto jobDto, int id, int workspaceId);

    JobModel update(JobDto jobDto, int jobId);

    void delete(int id);

    JobModel findById(int id);

}
