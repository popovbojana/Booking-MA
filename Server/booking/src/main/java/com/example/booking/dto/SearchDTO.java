package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String accommodationName;
    private LocalDateTime dateFrom;
    private LocalDateTime dateUntil;
}
