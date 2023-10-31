package com.robertmartins.notesapi.domain.models;

import com.robertmartins.notesapi.infraestructure.configs.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "koda_workspace_comment")
@Getter
@Setter
public class WorkspaceComment extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id")
    private Workspace workspace;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

}
