package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.WorkspaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceModel, Integer> {
}
