package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.models.JobStatusModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobStatusResource {

    JobStatusModel save(JobStatusDto jobStatusDto, int id);

    JobStatusModel update(JobStatusDto jobStatusDto, int id);

    JobStatusModel findById(int id);

    List<JobStatusModel> createGenericStatus();

    boolean jobStatusExists(int id);

}
