package com.robertmartins.notesapi.domain.models;

import com.robertmartins.notesapi.infraestructure.configs.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "koda_job_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobStatus extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "position", nullable = false)
    private int position;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "jobStatus", orphanRemoval = true)
    private Job job;

}
