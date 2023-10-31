package com.robertmartins.notesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<com.robertmartins.notesapi.models.Job, Integer> {

    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE id = :id")
    void deleteJobById(@Param("id") int id);

}
