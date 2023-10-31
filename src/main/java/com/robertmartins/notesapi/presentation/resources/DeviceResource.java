package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.UserDeviceDto;
import org.springframework.stereotype.Service;

@Service
public interface DeviceResource {

    UserDeviceModel findById(int id);

    UserDeviceModel changeDeviceStatus(int id);

    void deleteById(int id);

    UserDeviceModel setDevice(UserDeviceDto userDeviceDto);

}
