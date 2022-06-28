package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.JobStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatusModel, Integer> {
}
