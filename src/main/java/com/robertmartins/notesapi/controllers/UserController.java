package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.services.AddressService;
import com.robertmartins.notesapi.services.ProfileService;
import com.robertmartins.notesapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;

    final ProfileService profileService;

    final AddressService addressService;

    public UserController(UserService userService, ProfileService profileService, AddressService addressService){
        this.userService = userService;
        this.profileService = profileService;
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDto userDto){
        if(userService.existsByLogin(userDto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email already in use");
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }



}
