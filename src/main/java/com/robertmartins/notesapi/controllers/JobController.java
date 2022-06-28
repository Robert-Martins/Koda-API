package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{id}/workspace/{workspaceId}/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

}
