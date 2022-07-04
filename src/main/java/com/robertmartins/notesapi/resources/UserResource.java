package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.UserModel;

import java.util.Optional;

public interface UserResource {

    UserModel save(UserDto userDto);

    UserModel updateCredentials(UserCredentialsDto userCredentialsDto, int id);

    UserModel saveUser(UserModel user);

    Optional<UserModel> findById(int id);

    void deleteById(int id);

    boolean existsByLogin(String login);

}
