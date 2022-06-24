package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/profile")
public class ProfileController {

    final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    public ProfileModel setProfile(ProfileDto profileDto, AddressModel addressModel, String email){
        var profile = new ProfileModel();
        profile.setName(profileDto.getName());
        profile.setCpf(profileDto.getCpf());
        profile.setEmail(email);
        profile.setTelephone(profileDto.getTelephone());
        profile.setBirthDate(profileDto.getBirthDate());
        profile.setAddress(addressModel);
        profile.setUpdatedAt(new Date());
        profile.setCreatedAt(new Date());
        return profile;
    }

}
