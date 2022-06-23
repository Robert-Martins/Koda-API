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
        var addressModel = new AddressModel();
        addressModel.setCountry(userDto.getCountry());
        addressModel.setPostalCode(userDto.getPostalCode());
        addressModel.setUf(userDto.getUf());
        addressModel.setCity(userDto.getCity());
        addressModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        addressModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        addressService.save(addressModel);
        var profileModel = new ProfileModel();
        profileModel.setName(userDto.getName());
        profileModel.setCpf(userDto.getCpf());
        profileModel.setBirthDate(userDto.getBirthDate());
        profileModel.setTelephone(userDto.getTelephone());
        profileModel.setEmail(userDto.getEmail());
        profileModel.setAddress(addressModel);
        profileModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        profileModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        profileService.save(profileModel);
        var userModel = new UserModel();
        userModel.setLogin(userDto.getEmail());
        userModel.setPassword(userDto.getPassword());
        userModel.setProfile(profileModel);
        userModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") int id){
        var user = userService.findById(id);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

}
