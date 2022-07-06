package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AddressResource {

    AddressModel update(AddressDto addressDto, int id);

    AddressModel setAddress(AddressDto addressDto);

}
