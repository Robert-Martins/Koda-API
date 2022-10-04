package com.robertmartins.notesapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponseDto {

    private int id;

    private String operationType;

    private int status;

    private String message;

    private LocalDateTime timestamp;

}
