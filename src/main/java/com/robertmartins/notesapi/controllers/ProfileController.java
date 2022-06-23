package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/profile")
public class ProfileController {

    final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ProfileDto profileDto){
        if(profileService.existsByEmail(profileDto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Duplicated E-mail");
        var profileModel = new ProfileModel();
        var userModel = new UserModel();
        profileModel.setName(profileDto.getName());
        profileModel.setCpf(profileDto.getCpf());
        profileModel.setBirthDate(profileDto.getBirthDate());
        profileModel.setTelephone(profileDto.getTelephone());
        profileModel.setEmail(profileDto.getEmail());
        profileModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        profileModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLogin(profileDto.getEmail());
        userModel.setPassword(profileDto.getPassword());
        userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(profileModel));
    }

}
