package com.robertmartins.notesapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaginatedResponseDto {

    private Object content;

    private int page;

    private int itemsPerPage;

    private int totalPages;

    private String orderedBy;

    private String order;

    private LocalDateTime timestamp;

}
