package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileModel save(ProfileModel profileModel){
        return profileRepository.save(profileModel);
    }

    public Optional<ProfileModel> findById(int id){
        return profileRepository.findById(id);
    }

    public boolean existsByEmail(String email){
        return profileRepository.existsByEmail(email);
    }

}
