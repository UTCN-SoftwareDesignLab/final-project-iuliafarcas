package com.lab4.demo.court.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class BookingFilterDTO {
    @Builder.Default
    private final LocalDateTime startDate = null;
}
