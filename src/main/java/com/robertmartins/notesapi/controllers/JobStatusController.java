package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.JobStatusDto;
import com.robertmartins.notesapi.models.JobStatusModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class JobStatusController {

    public List<JobStatusModel> createGenericStatus(){
        List<JobStatusModel> jobStatusList = new ArrayList<>();
        jobStatusList.add(new JobStatusModel("To-Do", "Jobs to be done", "##ff1c37", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Development", "Jobs in development", "##ffc800", new Date(), new Date()));
        jobStatusList.add(new JobStatusModel("Done", "Jobs done", "##29e000", new Date(), new Date()));
        return jobStatusList;
    }

}
