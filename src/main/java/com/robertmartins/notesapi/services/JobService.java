package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;
import com.robertmartins.notesapi.repositories.JobRepository;
import com.robertmartins.notesapi.resources.JobResource;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JobService implements JobResource {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkspaceResource workspaceResource;

    @Autowired
    private JobStatusResource jobStatusResource;

    public JobModel save(JobDto jobDto, int workspaceId){
        var workspace = workspaceResource.findById(workspaceId);
        var status = jobStatusResource.findById(jobDto.getStatusId());
        var jobList = workspace.get().getJobs();
        var job = new JobModel();
        job.setName(jobDto.getName());
        job.setDescription(jobDto.getDescription());
        job.setJobStatus(status.get());
        job.setUpdatedAt(new Date());
        job.setCreatedAt(new Date());
        jobList.add(job);
        workspace.get().setJobs(jobList);
        workspaceResource.saveWorkspace(workspace.get());
        return job;
    }

    public JobModel update(JobDto jobDto, int jobId){
        var status = jobStatusResource.findById(jobDto.getStatusId());
        var job = this.findById(jobId);
        job.get().setName(jobDto.getName());
        job.get().setDescription(jobDto.getDescription());
        job.get().setJobStatus(status.get());
        job.get().setUpdatedAt(new Date());
        return jobRepository.save(job.get());
    }

    public Optional<JobModel> findById(int id){
        return jobRepository.findById(id);
    }

    public void delete(int id){
        jobRepository.deleteById(id);
    }

}
