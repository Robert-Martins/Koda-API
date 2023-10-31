package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.*;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserResource userResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PostMapping("/login")
    public ResponseEntity<ClientResponseDto> login(Authentication authentication, @RequestBody @Valid UserDeviceDto userDevice){
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientResponseDto.builder()
                        .id(0)
                        .operationType("AUTHENTICATION")
                        .status(HttpStatus.OK.value())
                        .message(userResource.login(authentication, userDevice))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

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
    public ResponseEntity<com.robertmartins.notesapi.models.User> getUserById(Authentication authentication, @PathVariable(name = "id") int id){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.OK).body(userResource.findById(id));
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<PaginatedResponseDto> getAllUserDevices(Authentication authentication, @PathVariable(name = "id") int id){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        return ResponseEntity.status(HttpStatus.OK).body(
                PaginatedResponseDto.builder()
                        .content(userResource.findAllUserDevices(id))
                        .page(0)
                        .itemsPerPage(0)
                        .totalPages(0)
                        .orderedBy(" ")
                        .order(" ")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateUserCredentialsById(Authentication authentication, @PathVariable(name = "id") int id, @RequestBody @Valid UserCredentialsDto userCredentials){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
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
    public ResponseEntity<ClientResponseDto> deleteUserById(Authentication authentication, @PathVariable(name = "id") int id){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
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
