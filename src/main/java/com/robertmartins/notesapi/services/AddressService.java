package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressModel save(AddressModel addressModel){
        return addressRepository.save(addressModel);
    }

    public Optional<AddressModel> findById(int id) {
        return addressRepository.findById(id);
    }

}
