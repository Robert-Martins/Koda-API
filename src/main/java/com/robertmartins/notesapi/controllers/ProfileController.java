package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.resources.ProfileResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/profile")
public class ProfileController {

    @Autowired
    private ProfileResource profileResource;

    @Autowired
    private UserResource userResource;

    @PutMapping
    public ResponseEntity<ProfileModel> updateUserProfileById(@PathVariable(name = "id") int profileId, @RequestBody @Valid UserProfileDto profileDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(profileResource.update(profileDto, profileId));
    }

}
