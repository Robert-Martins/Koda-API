package com.robertmartins.notesapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponseDto {

    private String message;

    private LocalDateTime timestamp;

}
