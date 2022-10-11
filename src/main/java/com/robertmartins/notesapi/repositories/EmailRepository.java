package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Integer> {
}
