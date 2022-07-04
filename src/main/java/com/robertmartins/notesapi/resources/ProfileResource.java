package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.ProfileDto;
import com.robertmartins.notesapi.dtos.UserProfileDto;
import com.robertmartins.notesapi.models.AddressModel;
import com.robertmartins.notesapi.models.ProfileModel;

public interface ProfileResource {

    ProfileModel update(UserProfileDto profileDto, int id);

    ProfileModel setProfile(ProfileDto profileDto, AddressModel addressModel);

}
