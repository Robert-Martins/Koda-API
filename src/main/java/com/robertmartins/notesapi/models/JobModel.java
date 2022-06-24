package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_WORKSPACES_JOBS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creatorAndAssignId", referencedColumnName = "id")
    private UserModel userModel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jobStatusId", referencedColumnName = "id")
    private JobStatusModel jobStatusModel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jobCommentId", referencedColumnName = "id")
    private List<CommentModel> commentModels;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
