package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("user/{id}/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

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
