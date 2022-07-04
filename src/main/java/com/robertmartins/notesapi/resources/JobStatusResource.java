package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.models.JobStatusModel;

import java.util.List;
import java.util.Optional;

public interface JobStatusResource {

    JobStatusModel save(JobStatusDto jobStatusDto, int id);

    JobStatusModel update(JobStatusDto jobStatusDto, int id);

    void deleteById(int id);

    Optional<JobStatusModel> findById(int id);

    List<JobStatusModel> createGenericStatus();

}
