package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;
import com.robertmartins.notesapi.services.AddressService;
import com.robertmartins.notesapi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProfileService profileService;

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
