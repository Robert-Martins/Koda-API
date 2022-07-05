package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.UserProfileDto;
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
    public ResponseEntity<Object> updateUserProfileById(@PathVariable(name = "id") int id, @RequestBody @Valid UserProfileDto profileDto){
        Optional<UserModel> user = userResource.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.CREATED).body(profileResource.update(profileDto, id));
    }

}
