package com.robertmartins.notesapi.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robertmartins.notesapi.infraestructure.configs.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "koda_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "job_status_id", referencedColumnName = "id")
    private JobStatus jobStatus;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "job", orphanRemoval = true)
    private List<JobComment> comments;

}
