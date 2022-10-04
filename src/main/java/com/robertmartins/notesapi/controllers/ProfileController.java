package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.resources.ProfileResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/{id}/profile")
public class ProfileController {

    @Autowired
    private ProfileResource profileResource;

    @Autowired
    private UserResource userResource;

    @PutMapping
    public ResponseEntity<ClientResponseDto> updateUserProfileById(@PathVariable(name = "id") int profileId, @RequestBody @Valid UserProfileDto profileDto){
        profileResource.update(profileDto, profileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(profileId)
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("User Profile Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
