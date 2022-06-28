package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobModel, Integer> {
}
