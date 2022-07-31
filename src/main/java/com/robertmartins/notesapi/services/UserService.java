package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.exceptions.DuplicateKeyException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.repositories.ProfileRepository;
import com.robertmartins.notesapi.repositories.UserRepository;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserService implements UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    public UserModel save(UserDto userDto){
        if(profileRepository.existsByEmail(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Email already in use");
        if(userRepository.existsByLogin(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = new UserModel();
        var address = addressResource.setAddress(userDto.getProfile().getAddress());
        var profile = profileService.setProfile(userDto.getProfile(), address);
        user.setLogin(userDto.getProfile().getEmail());
        user.setPassword(userDto.getPassword());
        user.setProfile(profile);
        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    public UserModel updateCredentials(UserCredentialsDto userCredentialsDto, int id){
        if(userRepository.existsByLogin(userCredentialsDto.getLogin()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = this.findById(id);
        user.setLogin(userCredentialsDto.getLogin());
        user.setPassword(userCredentialsDto.getPassword());
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public UserModel findById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public boolean existsByLogin(String login){
        return userRepository.existsByLogin(login);
    }

}
