package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_JOBS_STATUS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "position", nullable = false)
    private int position;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    public JobStatusModel(String name, String description, String color, int position, Date updatedAt, Date createdAt){
        this.setName(name);
        this.setDescription(description);
        this.setColor(color);
        this.setPosition(position);
        this.setUpdatedAt(updatedAt);
        this.setCreatedAt(createdAt);
    }

}
