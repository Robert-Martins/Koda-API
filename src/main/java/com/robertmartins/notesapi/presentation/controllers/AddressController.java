package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.dtos.ClientResponseDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/address")
public class AddressController {

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PutMapping("/{addressId}")
    public ResponseEntity<ClientResponseDto> updateUserAddress(Authentication authentication, @PathVariable(name = "id") int id, @PathVariable(name = "addressId") int addressId, @RequestBody @Valid AddressDto addressDto){
        if(!authorizationResource.checkJwtAuthorization(id, authentication.getName()))
            throw new ActionNotAllowedException();
        if(!addressResource.addressExists(addressId))
            throw new ResourceNotFoundException("Address Not Found");
        if(!authorizationResource.itIsUserAddress(id, addressId))
            throw new ActionNotAllowedException();
        addressResource.update(addressDto, addressId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClientResponseDto.builder()
                        .id(id)
                        .operationType("UPDATE")
                        .status(HttpStatus.OK.value())
                        .message("User Address Updated Successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
