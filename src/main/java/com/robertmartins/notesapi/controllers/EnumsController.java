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

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping("/uf")
    public ResponseEntity<Map<Integer, EnumDto>> getUfEnums(){
        return ResponseEntity.status(HttpStatus.OK).body(buildUfEnumList());
    }

    public Map<Integer, EnumDto> buildUfEnumList(){
        Map<Integer, EnumDto> enumDtoMap = new HashMap<>();
        var count = 1;
        for(UfEnum e : UfEnum.values()){
            var enumDto = new EnumDto(e.toString(), e.getName());
            enumDtoMap.put(count, enumDto);
            count++;
        }
        return enumDtoMap;
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRolesEnums(){
        return ResponseEntity.status(HttpStatus.OK).body(buildRolesEnumList());
    }

    public Map<Integer, EnumDto> buildRolesEnumList(){
        Map<Integer, EnumDto> enumDtoMap = new HashMap<>();
        var count = 1;
        for(UserRolesEnum e : UserRolesEnum.values()){
            var enumDto = new EnumDto(e.toString(), e.getDescription());
            enumDtoMap.put(count, enumDto);
            count++;
        }
        return enumDtoMap;
    }

}
