package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.DeviceResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/device")
public class DeviceController {

    @Autowired
    private DeviceResource deviceResource;

    @Autowired
    private UserResource userResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PutMapping("/{deviceId}")
    public ResponseEntity<ClientResponseDto> changeDeviceStatus(Authentication authentication, @PathVariable(name = "id") int id, @PathVariable(name = "deviceId") int deviceId){
        if(
                !authorizationResource.checkJwtAuthorization(id, authentication.getName()) ||
                !authorizationResource.itIsUserDevice(id, deviceId)
        )
            throw new ActionNotAllowedException();
        var device = deviceResource.changeDeviceStatus(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(deviceId)
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message(device.isDeviceAccessBlocked() ? "Device Access Blocked" : "Device Access Allowed")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ClientResponseDto> deleteById(Authentication authentication, @PathVariable(name = "id") int id, @PathVariable(name = "deviceId") int deviceId){
        if(
                !authorizationResource.checkJwtAuthorization(id, authentication.getName()) ||
                !authorizationResource.itIsUserDevice(id, deviceId)
        )
            throw new ActionNotAllowedException();
        userResource.deleteUserDeviceById(id, deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(0)
                        .operationType("DELETE")
                        .status(HttpStatus.OK.value())
                        .message("Device Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
