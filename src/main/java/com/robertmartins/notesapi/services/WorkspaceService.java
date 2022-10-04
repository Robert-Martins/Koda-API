package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.NewWorkspaceDto;
import com.robertmartins.notesapi.dtos.WorkspaceListDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.WorkspaceModel;
import com.robertmartins.notesapi.repositories.CommentRepository;
import com.robertmartins.notesapi.repositories.JobRepository;
import com.robertmartins.notesapi.repositories.JobStatusRepository;
import com.robertmartins.notesapi.repositories.WorkspaceRepository;
import com.robertmartins.notesapi.resources.JobStatusResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.resources.WorkspaceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkspaceService implements WorkspaceResource {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobStatusRepository jobStatusRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private JobStatusResource jobStatusResource;

    public WorkspaceModel save(NewWorkspaceDto workspaceDto, int id){
        var user = userResource.findById(id);
        var workspace = this.setWorkspace(workspaceDto);
        List<WorkspaceModel> workspaceModelList = user.getWorkspaces();
        workspaceModelList.add(workspace);
        user.setWorkspaces(workspaceModelList);
        userResource.saveUser(user);
        return workspaceModelList.get(workspaceModelList.size() - 1);
    }

    @Transactional
    public WorkspaceModel update(NewWorkspaceDto workspaceDto, int workspaceId){
        var workspace = this.findById(workspaceId);
        workspace.setName(workspaceDto.getName());
        workspace.setDescription(workspaceDto.getDescription());
        workspace.setUpdatedAt(new Date());
        return workspaceRepository.save(workspace);
    }

    @Transactional
    public void deleteById(int workspaceId, int id){
        var workspace = this.findById(workspaceId);
        var status = workspace.getJobStatus();
        if(!(status == null || status.size() == 0))
            for(var s : status)
                this.deleteStatusById(workspaceId, s.getId());
        workspaceRepository.deleteWorkspaceById(workspaceId);
    }

    public WorkspaceModel findById(int id){
        jobStatusResource.organizePositions(id);
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace Not Found"));
    }

    public List<WorkspaceListDto> findAll(int id){
        var user = userResource.findById(id);
        var workspaces = user.getWorkspaces();
        List<WorkspaceListDto> workspacesList = new ArrayList<>();
        workspaces.forEach(w -> {
            var workspace = new WorkspaceListDto();
            workspace.setId(w.getId());
            workspace.setName(w.getName());
            workspace.setDescription(w.getDescription());
            workspace.setJobCount((w.getJobs() == null || w.getJobs().size() == 0) ? 0 : w.getJobs().size());
            workspace.setUpdatedAt(w.getUpdatedAt());
            workspace.setCreatedAt(w.getCreatedAt());
            workspacesList.add(workspace);
        });
        return workspacesList;
    }

    @Transactional
    public void deleteJobById(int workspaceId, int id){
        var job = jobRepository.findById(id).orElseThrow();
        var comments = job.getComments();
        if(!( comments == null || comments.size() == 0))
            for(var comment : comments)
                commentRepository.deleteCommentById(comment.getId());
        jobRepository.deleteJobById(id);
    }

    @Transactional
    public void deleteStatusById(int workspaceId, int id){
        var workspace = this.findById(workspaceId);
        var jobStatus = jobStatusResource.findById(id);
        var jobs = workspace.getJobs().stream()
                        .filter(job -> job.getJobStatus().getId() == jobStatus.getId())
                        .collect(Collectors.toList());
        jobs.forEach(jobModel -> this.deleteJobById(workspaceId, jobModel.getId()));
        jobStatusRepository.deleteStatusById(id);
    }

    public boolean workspaceExists(int id){
        return workspaceRepository.existsById(id);
    }

    public WorkspaceModel setWorkspace(NewWorkspaceDto newWorkspaceDto){
        var workspace = new WorkspaceModel();
        workspace.setName(newWorkspaceDto.getName());
        workspace.setDescription(newWorkspaceDto.getDescription());
        workspace.setUpdatedAt(new Date());
        if(workspace.getCreatedAt() == null){
            workspace.setJobStatus(jobStatusResource.createGenericStatus());
            workspace.setCreatedAt(new Date());
        }
        return workspace;
    }

}