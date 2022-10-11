package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.repositories.ProfileRepository;
import com.robertmartins.notesapi.repositories.UserRepository;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.ProfileResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Component
public class ProfileService implements ProfileResource {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressResource addressResource;

    @Transactional
    public ProfileModel update(UserProfileDto profileDto, int id){
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        var profile = user.getProfile();
        profile.setName(profileDto.getName());
        profile.setEmail(profileDto.getEmail());
        profile.setCpf(profileDto.getCpf());
        profile.setBirthDate(profileDto.getBirthDate());
        profile.setTelephone(profileDto.getTelephone());
        profile.setAddress(addressResource.update(profileDto.getAddress(), profile.getAddress().getId()));
        profile.setUpdatedAt(new Date());
        return profileRepository.save(profile);
    }

    private ProfileModel findById(int id) throws ResourceNotFoundException{
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile Not Found"));
    }

    public ProfileModel setProfile(ProfileDto profileDto, AddressModel addressModel){
        var profile = new ProfileModel();
        profile.setName(profileDto.getName());
        profile.setCpf(profileDto.getCpf());
        profile.setEmail(profileDto.getEmail());
        profile.setTelephone(profileDto.getTelephone());
        profile.setBirthDate(profileDto.getBirthDate());
        profile.setAddress(addressModel);
        profile.setUpdatedAt(new Date());
        profile.setCreatedAt(new Date());
        return profile;
    }

}
