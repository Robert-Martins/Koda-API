package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    boolean existsByUsername(String username);

    Optional<UserModel> findByUsername(String username);

}
