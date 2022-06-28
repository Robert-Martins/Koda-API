package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.JobStatusModel;
import com.robertmartins.notesapi.repositories.JobStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobStatusService {

    @Autowired
    private JobStatusRepository jobStatusRepository;

    public JobStatusModel save(JobStatusModel jobStatusModel){
        return jobStatusRepository.save(jobStatusModel);
    }

    public Optional<JobStatusModel> findById(int id){
        return jobStatusRepository.findById(id);
    }

    public void deleteById(int id){
        jobStatusRepository.deleteById(id);
    }

}
