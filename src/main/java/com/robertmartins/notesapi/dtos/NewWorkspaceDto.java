package com.robertmartins.notesapi.dtos;

import com.robertmartins.notesapi.models.JobStatusModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class NewWorkspaceDto {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    private List<JobStatusModel> jobStatusModelList;

}
