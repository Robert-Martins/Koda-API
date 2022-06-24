package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.services.AddressService;
import com.robertmartins.notesapi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("user/{id}/address")
public class AddressController {

    final AddressService addressService;

    final ProfileService profileService;

    public AddressController(AddressService addressService, ProfileService profileService){
        this.addressService = addressService;
        this.profileService = profileService;
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
