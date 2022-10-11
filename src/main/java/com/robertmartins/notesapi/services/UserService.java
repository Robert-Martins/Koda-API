package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserCredentialsDto;
import com.robertmartins.notesapi.dtos.UserDeviceDto;
import com.robertmartins.notesapi.dtos.UserDto;
import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.DuplicateKeyException;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import com.robertmartins.notesapi.models.UserDeviceModel;
import com.robertmartins.notesapi.models.UserModel;
import com.robertmartins.notesapi.repositories.ProfileRepository;
import com.robertmartins.notesapi.repositories.UserRepository;
import com.robertmartins.notesapi.resources.AddressResource;
import com.robertmartins.notesapi.resources.DeviceResource;
import com.robertmartins.notesapi.resources.EmailResource;
import com.robertmartins.notesapi.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserService implements UserResource{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressResource addressResource;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private DeviceResource deviceResource;

    @Autowired
    private EmailResource emailResource;

    @Autowired
    private JwtEncoder jwtEncoder;

    public UserModel save(UserDto userDto) throws DuplicateKeyException{
        if(profileRepository.existsByEmail(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Email already in use");
        if(userRepository.existsByUsername(userDto.getProfile().getEmail()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = new UserModel();
        var address = addressResource.setAddress(userDto.getProfile().getAddress());
        var profile = profileService.setProfile(userDto.getProfile(), address);
        List<UserDeviceModel> deviceList = new ArrayList<>(){{
            add(deviceResource.setDevice(userDto.getDevice()));
        }};
        user.setUsername(userDto.getProfile().getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setProfile(profile);
        user.setDevices(deviceList);
        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        emailResource.sendNewUserEmail(userDto.getProfile().getEmail());
        return userRepository.save(user);
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    @Transactional
    public UserModel updateCredentials(UserCredentialsDto userCredentialsDto, int id) throws DuplicateKeyException{
        if(userRepository.existsByUsername(userCredentialsDto.getLogin()))
            throw new DuplicateKeyException("Conflict: Login already in use");
        var user = this.findById(id);
        user.setUsername(userCredentialsDto.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(userCredentialsDto.getPassword()));
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public UserModel findById(int id) throws ResourceNotFoundException{
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public List<UserDeviceModel> findAllUserDevices(int id){
        return this.findById(id).getDevices();
    }

    public String login(Authentication authentication, UserDeviceDto userDevice){
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with Username: "+authentication.getName()));
        var deviceList = user.getDevices();
        var deviceExists = deviceList.stream().anyMatch(d -> Objects.equals(d.getIp(), userDevice.getIp()));
        if(deviceExists){
            var device = deviceList.stream()
                    .filter(d -> Objects.equals(d.getIp(), userDevice.getIp()))
                    .findAny()
                    .orElseThrow(() -> new ResourceNotFoundException("Device Not Found"));
            if(device.isDeviceAccessBlocked()){
                emailResource.sendLoginAttemptInBlockedIpEmail(user.getProfile().getEmail());
                throw new ActionNotAllowedException();
            }
        }
        if(!deviceExists){
            deviceList.add(deviceResource.setDevice(userDevice));
            user.setDevices(deviceList);
            userRepository.save(user);
            emailResource.sendLoginInNewIpEmail(user.getProfile().getEmail(), userDevice);
        }
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Transactional
    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserDeviceById(int id, int deviceId){
        var user = this.findById(id);
        var devices = user.getDevices();
        var device = deviceResource.findById(deviceId);
        devices.remove(device);
        user.setDevices(devices);
        userRepository.save(user);
        deviceResource.deleteById(deviceId);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

}
