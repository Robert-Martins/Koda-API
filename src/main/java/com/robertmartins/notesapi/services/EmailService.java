package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserDeviceDto;
import com.robertmartins.notesapi.resources.EmailResource;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements EmailResource {

    public void sendNewUserEmail(String emailTo){

    }

    public void sendLoginInNewIpEmail(String emailTo, UserDeviceDto userDeviceDto){

    }

    public void sendLoginAttemptInBlockedIpEmail(String emailTo) {

    }

    private void sendEmail(String emailTo, String subject, String text){

    }

}
