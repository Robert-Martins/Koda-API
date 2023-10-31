package com.robertmartins.notesapi.resources;

import com.robertmartins.notesapi.dtos.UserDeviceDto;
import org.springframework.stereotype.Service;

@Service
public interface EmailResource {

    void sendNewUserEmail(String emailTo);

    void sendLoginInNewIpEmail(String emailTo, UserDeviceDto userDeviceDto);

    void sendLoginAttemptInBlockedIpEmail(String emailTo);

}
