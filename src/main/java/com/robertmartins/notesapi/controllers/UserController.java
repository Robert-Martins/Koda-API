package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.UserResource;
import com.robertmartins.notesapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserResource userResource;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDto userDto){
        if(userResource.existsByLogin(userDto.getProfile().getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email already in use");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResource.save(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") int id){
        var user = userResource.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserCredentialsById(@PathVariable(name = "id") int id, @RequestBody @Valid UserCredentialsDto userCredentials){
        var user = userResource.findById(id);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResource.updateCredentials(userCredentials, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable(name = "id") int id){
        userResource.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

}
