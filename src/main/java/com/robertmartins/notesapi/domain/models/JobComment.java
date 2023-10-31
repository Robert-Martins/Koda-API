package com.robertmartins.notesapi.domain.models;

import com.robertmartins.notesapi.infraestructure.configs.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "koda_job_comment")
@Getter
@Setter
public class JobComment extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Comment comment;

}
