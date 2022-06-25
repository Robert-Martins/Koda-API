package com.robertmartins.notesapi.controllers;

import com.robertmartins.notesapi.dtos.EnumDto;
import com.robertmartins.notesapi.enums.UfEnum;
import com.robertmartins.notesapi.enums.UserRolesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping("/uf")
    public ResponseEntity<Object> getUfEnums(){
        return ResponseEntity.status(HttpStatus.OK).body(buildUfEnumList());
    }

    public List<EnumDto> buildUfEnumList(){
        List<EnumDto> enumDtoList = new ArrayList();
        for(UfEnum e : UfEnum.values()){
            var enumDto = new EnumDto(e.toString(), e.getName());
            enumDtoList.add(enumDto);
        }
        return enumDtoList;
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRolesEnums(){
        return ResponseEntity.status(HttpStatus.OK).body(buildRolesEnumList());
    }

    public List<EnumDto> buildRolesEnumList(){
        List<EnumDto> enumDtoList = new ArrayList();
        for(UserRolesEnum e : UserRolesEnum.values()){
            var enumDto = new EnumDto(e.toString(), e.getDescription());
            enumDtoList.add(enumDto);
        }
        return enumDtoList;
    }

}
