package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.AddressDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.repositories.AddressRepository;
import com.robertmartins.notesapi.resources.AddressResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AddressService implements AddressResource {

    @Autowired
    private AddressRepository addressRepository;

    public AddressModel update(AddressDto addressDto, int id){
        var address = this.findById(id);
        BeanUtils.copyProperties(addressDto, address.get());
        address.get().setUpdatedAt(new Date());
        return addressRepository.save(address.get());
    }

    private Optional<AddressModel> findById(int id) {
        return addressRepository.findById(id);
    }

    public AddressModel setAddress(AddressDto addressDto){
        var address = new AddressModel();
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setUf(addressDto.getUf());
        address.setCity(addressDto.getCity());
        address.setUpdatedAt(new Date());
        if(address.getCreatedAt() == null)
            address.setCreatedAt(new Date());
        return address;
    }

}
