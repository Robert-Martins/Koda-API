package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    boolean existsByLogin(String login);

}
