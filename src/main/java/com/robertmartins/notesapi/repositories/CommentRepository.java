package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel, Integer> {
}
