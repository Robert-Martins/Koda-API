package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.services.ProfileService;
import com.robertmartins.notesapi.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/profile/")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<Object> updateUserProfileById(@PathVariable(name = "id") int id, @RequestBody @Valid UserProfileDto profileDto){
        Optional<UserModel> user = userService.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        var profile = user.get().getProfile();
        profile.setUpdatedAt(new Date());
        profile.setName(profileDto.getName());
        profile.setCpf(profileDto.getCpf());
        profile.setEmail(profileDto.getEmail());
        profile.setAddress(profileDto.getAddress());
        profile.setBirthDate(profileDto.getBirthDate());
        profile.setTelephone(profileDto.getTelephone());
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(profile));
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
