package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USERS_WORKSPACES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    private String description;

    private UserModel user;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
