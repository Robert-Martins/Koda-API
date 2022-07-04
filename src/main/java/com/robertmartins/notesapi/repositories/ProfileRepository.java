package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileModel, Integer> {
}
