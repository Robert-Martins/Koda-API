package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_GROUPS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    private String description;

    private UserModel admin;

    private List<GroupWorkspaceModel> groupWorkspaces;

    private List<UserModel> groupUsers;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
