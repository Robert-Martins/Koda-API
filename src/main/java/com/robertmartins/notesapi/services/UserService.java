package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.repositories.UserRepository;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.ProfileResource;
import com.robertmartins.notesapi.resources.UserResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Component
public class UserService implements UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private ProfileService profileService;

    public UserModel save(UserDto userDto){
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
        var user = this.findById(id);
        user.get().setLogin(userCredentialsDto.getLogin());
        user.get().setPassword(userCredentialsDto.getPassword());
        user.get().setUpdatedAt(new Date());
        return userRepository.save(user.get());
    }

    public Optional<UserModel> findById(int id){
        return userRepository.findById(id);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public boolean existsByLogin(String login){
        return userRepository.existsByLogin(login);
    }

}
