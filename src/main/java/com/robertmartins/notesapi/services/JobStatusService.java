package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.repositories.JobStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobStatusService {

    @Autowired
    private JobStatusRepository jobStatusRepository;

}
