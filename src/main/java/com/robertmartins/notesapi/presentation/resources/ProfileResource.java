package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.models.AddressModel;
import org.springframework.stereotype.Service;

@Service
public interface ProfileResource {

    ProfileModel update(UserProfileDto profileDto, int id);

    ProfileModel setProfile(ProfileDto profileDto, AddressModel addressModel);

}
