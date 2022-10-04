package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.repositories.JobStatusRepository;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobStatusResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobStatusService implements JobStatusResource {

    @Autowired
    private JobStatusRepository jobStatusRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public JobStatusModel save(JobStatusDto jobStatusDto, int id){
        var workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace Not Found"));
        var jobStatusList = workspace.getJobStatus();
        var jobStatus = new JobStatusModel();
        BeanUtils.copyProperties(jobStatusDto, jobStatus);
        jobStatus.setPosition(jobStatusList.size() + 1);
        jobStatus.setUpdatedAt(new Date());
        jobStatus.setCreatedAt(new Date());
        jobStatusList.add(jobStatus);
        workspace.setJobStatus(jobStatusList);
        workspaceRepository.save(workspace);
        return jobStatusList.get(jobStatusList.size() - 1);
    }

    @Transactional
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

    public JobStatusModel changePosition(int id, int workspaceId, int position){
        var workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace Not Found"));
        var jobStatus = this.findById(id);
        var jobStatusList = workspace.getJobStatus();
        var sortedList = jobStatusList.stream()
                .sorted(Comparator.comparing(JobStatusModel::getPosition))
                .collect(Collectors.toList());
        var statusPosition = jobStatus.getPosition();
        sortedList.remove(statusPosition - 1);
        sortedList.add(position - 1, jobStatus);
        jobStatusList.stream()
                .forEach(status -> {
                    var p = sortedList.indexOf(status);
                    status.setPosition(p + 1);
                    status.setUpdatedAt(new Date());
                    jobStatusRepository.save(status);
                });
        return this.findById(id);
    }

    public boolean jobStatusExists(int id){
        return jobStatusRepository.existsById(id);
    }

    public List<JobStatusModel> createGenericStatus(){
        List<JobStatusModel> jobStatusList = new ArrayList<>();
        jobStatusList.add(new JobStatusModel("To-Do", "Jobs to be done", "#ff1c37", 1, new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Development", "Jobs in development", "#ffc800", 2, new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Done", "Jobs done", "#29e000", 3, new Date(), new Date()));
        return jobStatusList;
    }

}
