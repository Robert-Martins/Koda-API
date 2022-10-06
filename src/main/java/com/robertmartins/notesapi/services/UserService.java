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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
public class UserService implements UserResource{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    public UserModel save(UserDto userDto) throws DuplicateKeyException{
        if(profileRepository.existsByEmail(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Email already in use");
        if(userRepository.existsByUsername(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = new UserModel();
        var address = addressResource.setAddress(userDto.getProfile().getAddress());
        var profile = profileService.setProfile(userDto.getProfile(), address);
        user.setUsername(userDto.getProfile().getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setProfile(profile);
        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    @Transactional
    public UserModel updateCredentials(UserCredentialsDto userCredentialsDto, int id) throws DuplicateKeyException{
        if(userRepository.existsByUsername(userCredentialsDto.getLogin()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = this.findById(id);
        user.setUsername(userCredentialsDto.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(userCredentialsDto.getPassword()));
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public UserModel findById(int id) throws ResourceNotFoundException{
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Transactional
    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

}
