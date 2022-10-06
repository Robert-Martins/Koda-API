package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserResource {

    UserModel save(UserDto userDto);

    UserModel updateCredentials(UserCredentialsDto userCredentialsDto, int id);

    UserModel saveUser(UserModel user);

    UserModel findById(int id);

    void deleteById(int id);

    String generateToken(Authentication authentication);

    boolean existsByUsername(String username);

}
