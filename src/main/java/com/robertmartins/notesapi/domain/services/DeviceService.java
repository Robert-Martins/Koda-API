package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserDeviceDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.repositories.DeviceRepository;
import com.robertmartins.notesapi.resources.DeviceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
public class DeviceService implements DeviceResource {

    @Autowired
    private DeviceRepository deviceRepository;

    public UserDeviceModel findById(int id){
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device Not Found"));
    }

    @Transactional
    public UserDeviceModel changeDeviceStatus(int id) {
        var device = this.findById(id);
        device.setDeviceAccessBlocked(!device.isDeviceAccessBlocked());
        device.setUpdatedAt(new Date());
        return deviceRepository.save(device);
    }

    public void deleteById(int id) {
        deviceRepository.deleteById(id);
    }

    public UserDeviceModel setDevice(UserDeviceDto userDeviceDto){
        var device = new UserDeviceModel();
        device.setIp(userDeviceDto.getIp());
        device.setOs(userDeviceDto.getOs());
        device.setBrowser(userDeviceDto.getBrowser());
        device.setAppVersion(userDeviceDto.getAppVersion());
        device.setBrowserLanguage(userDeviceDto.getBrowserLanguage());
        device.setDeviceAccessBlocked(false);
        device.setUpdatedAt(new Date());
        device.setCreatedAt(new Date());
        return device;
    }

}
