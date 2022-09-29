package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.JobModel;
import com.robertmartins.notesapi.repositories.JobRepository;
import com.robertmartins.notesapi.repositories.JobStatusRepository;
import com.robertmartins.notesapi.repositories.UserRepository;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
public class JobService implements JobResource {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private JobStatusRepository jobStatusRepository;

    public JobModel save(JobDto jobDto, int id, int workspaceId){
        var user = userRepository.findById(id);
        var workspace = workspaceRepository.findById(workspaceId);
        var workspaceStatus = workspace.get().getJobStatus();
        if(!workspaceStatus.stream().anyMatch(s -> s.getId() == jobDto.getStatusId()))
            throw new ActionNotAllowedException();
        var status = jobStatusRepository.findById(jobDto.getStatusId());
        var jobList = workspace.get().getJobs();
        var job = new JobModel();
        job.setName(jobDto.getName());
        job.setDescription(jobDto.getDescription());
        job.setJobStatus(status.get());
        job.setUser(user.get());
        job.setUpdatedAt(new Date());
        job.setCreatedAt(new Date());
        jobList.add(job);
        workspace.get().setJobs(jobList);
        workspaceRepository.save(workspace.get());
        return jobList.get(jobList.size() - 1);
    }

    @Transactional
    public JobModel update(JobDto jobDto, int jobId){
        var status = jobStatusRepository.findById(jobDto.getStatusId());
        var job = this.findById(jobId);
        job.setName(jobDto.getName());
        job.setDescription(jobDto.getDescription());
        job.setJobStatus(status.get());
        job.setUpdatedAt(new Date());
        return jobRepository.save(job);
    }

    public JobModel findById(int id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Not Found"));
    }

    public boolean jobExists(int id){
        return jobRepository.existsById(id);
    }

}
