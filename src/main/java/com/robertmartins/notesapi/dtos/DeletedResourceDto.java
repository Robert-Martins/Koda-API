package com.robertmartins.notesapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeletedResourceDto {

    private String message;

    private LocalDateTime timestamp;

}
