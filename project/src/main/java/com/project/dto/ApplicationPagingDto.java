package com.project.dto;

import java.util.List;

public record ApplicationPagingDto(
    int totalPages,
    long totallElements,
    List<ApplicationResponseDto>list
    ){
}
