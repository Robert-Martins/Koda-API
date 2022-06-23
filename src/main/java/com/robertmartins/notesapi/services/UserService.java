package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findById(int id){
        return userRepository.findById(id);
    }

    public boolean existsByLogin(String login){
        return userRepository.existsByLogin(login);
    }

}
