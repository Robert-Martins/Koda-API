package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.AuthorizationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/address")
public class AddressController {

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private AuthorizationResource authorizationResource;

    @PutMapping("/{addressId}")
    public ResponseEntity<Object> updateUserAddress(@PathVariable(name = "id") int id, @PathVariable(name = "addressId") int addressId, @RequestBody @Valid AddressDto addressDto){
        if(!authorizationResource.itIsUserAddress(id, addressId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        return ResponseEntity.status(HttpStatus.CREATED).body(addressResource.update(addressDto, addressId));
    }

}
