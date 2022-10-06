package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserResource userResource;

    @PostMapping
    public ResponseEntity<ClientResponseDto> save(@RequestBody @Valid UserDto userDto){
        var user = userResource.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(user.getId())
                        .operationType("CREATE")
                        .status(HttpStatus.CREATED.value())
                        .message("User Registered Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable(name = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(userResource.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateUserCredentialsById(@PathVariable(name = "id") int id, @RequestBody @Valid UserCredentialsDto userCredentials){
        var user = userResource.updateCredentials(userCredentials, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(user.getId())
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("User Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResponseDto> deleteUserById(@PathVariable(name = "id") int id){
        userResource.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .operationType("DELETE")
                        .status(HttpStatus.OK.value())
                        .message("User Deleted")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
