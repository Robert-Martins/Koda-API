package com.robertmartins.notesapi.repositories;

import com.robertmartins.notesapi.models.UserDeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<UserDeviceModel, Integer> {
}
