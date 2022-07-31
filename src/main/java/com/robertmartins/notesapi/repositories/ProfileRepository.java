package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel, Integer> {

    boolean existsByEmail(String email);

}
