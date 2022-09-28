package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.repositories.JobStatusRepository;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JobStatusService implements JobStatusResource {

    @Autowired
    private JobStatusRepository jobStatusRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public JobStatusModel save(JobStatusDto jobStatusDto, int id){
        var workspace = workspaceRepository.findById(id);
        var jobStatusList = workspace.get().getJobStatus();
        var jobStatus = new JobStatusModel();
        BeanUtils.copyProperties(jobStatusDto, jobStatus);
        jobStatus.setUpdatedAt(new Date());
        jobStatus.setCreatedAt(new Date());
        jobStatusList.add(jobStatus);
        workspace.get().setJobStatus(jobStatusList);
        workspaceRepository.save(workspace.get());
        return jobStatus;
    }

    public JobStatusModel update(JobStatusDto jobStatusDto, int id){
        var jobStatus = this.findById(id);
        BeanUtils.copyProperties(jobStatusDto, jobStatus);
        jobStatus.setUpdatedAt(new Date());
        jobStatusRepository.save(jobStatus);
        return jobStatus;
    }

    public JobStatusModel findById(int id){
        return jobStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status Not Found"));
    }

    public boolean jobStatusExists(int id){
        return jobStatusRepository.existsById(id);
    }

    public List<JobStatusModel> createGenericStatus(){
        List<JobStatusModel> jobStatusList = new ArrayList<>();
        jobStatusList.add(new JobStatusModel("To-Do", "Jobs to be done", "#ff1c37", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Development", "Jobs in development", "#ffc800", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Done", "Jobs done", "#29e000", new Date(), new Date()));
        return jobStatusList;
    }

}
