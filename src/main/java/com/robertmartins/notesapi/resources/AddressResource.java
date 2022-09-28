package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import org.springframework.stereotype.Service;

@Service
public interface AddressResource {

    AddressModel update(AddressDto addressDto, int id);

    AddressModel setAddress(AddressDto addressDto);

    boolean addressExists(int id);

}
