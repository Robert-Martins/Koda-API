package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.JobDto;
import com.robertmartins.notesapi.models.JobModel;
import com.robertmartins.notesapi.repositories.JobRepository;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkspaceResource workspaceResource;

    public JobModel save(JobDto jobDto, int workspaceId){
        var workspace = workspaceResource.findById(workspaceId);
        var jobList = workspace.get().getJobs();
        var job = new JobModel();
        BeanUtils.copyProperties(jobDto, job);
        job.setUpdatedAt(new Date());
        job.setCreatedAt(new Date());
        jobList.add(job);
        workspace.get().setJobs(jobList);
        workspaceResource.saveWorkspace(workspace.get());
        return job;
    }

    public Optional<JobModel> findById(int id){
        return jobRepository.findById(id);
    }

    public void deleteById(int id){
        jobRepository.deleteById(id);
    }

}
