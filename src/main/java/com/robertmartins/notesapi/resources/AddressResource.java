package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;

import java.util.Optional;

public interface AddressResource {

    AddressModel update(AddressDto addressDto, int id);

    AddressModel setAddress(AddressDto addressDto);

}
