package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.WorkspaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceModel, Integer> {

    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE id = :id")
    void deleteWorkspaceById(@Param("id") int id);

}
