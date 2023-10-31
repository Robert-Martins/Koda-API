package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDeviceDto;
import com.robertmartins.notesapi.dtos.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserResource {

    com.robertmartins.notesapi.models.User save(UserDto userDto);

    com.robertmartins.notesapi.models.User updateCredentials(UserCredentialsDto userCredentialsDto, int id);

    com.robertmartins.notesapi.models.User saveUser(com.robertmartins.notesapi.models.User user);

    com.robertmartins.notesapi.models.User findById(int id);

    List<UserDeviceModel> findAllUserDevices(int id);

    void deleteById(int id);

    void deleteUserDeviceById(int id, int deviceId);

    String login(Authentication authentication, UserDeviceDto userDevice);

    boolean existsByUsername(String username);

}
