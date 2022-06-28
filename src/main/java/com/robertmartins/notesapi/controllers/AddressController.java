package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.services.AddressService;
import jdk.jfr.BooleanFlag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthorizationController authorizationController;

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserAddress(@PathVariable(name = "id") int id, @PathVariable(name = "addressId") int addressId, @RequestBody @Valid AddressDto addressDto){
        if(!authorizationController.itIsUserAddress(id, addressId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action Not Allowed");
        var address = addressService.findById(addressId);
        BeanUtils.copyProperties(addressDto, address.get());
        address.get().setUpdatedAt(new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(address.get()));
    }

    public AddressModel setAddress(AddressDto addressDto){
        var address = new AddressModel();
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setUf(addressDto.getUf());
        address.setCity(addressDto.getCity());
        address.setUpdatedAt(new Date());
        address.setCreatedAt(new Date());
        return address;
    }

}
