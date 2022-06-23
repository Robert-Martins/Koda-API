package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Integer> {
}
