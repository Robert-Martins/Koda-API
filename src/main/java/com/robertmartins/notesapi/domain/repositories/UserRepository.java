package com.robertmartins.notesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.robertmartins.notesapi.models.User, Integer> {

    boolean existsByUsername(String username);

    Optional<com.robertmartins.notesapi.models.User> findByUsername(String username);

}
