package com.hotel.booking.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record createBookingDTO(
    
    @NotNull(message = "Username is required")
    String username, 

    @NotNull(message = "Room ID is required")
    Long roomId,
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    LocalDate checkInDate,
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Check-out date is required")
    LocalDate checkOutDate
) {
    @AssertTrue(message = "Check-out date must be after check-in date")
    public boolean isCheckinAfterCheckout() {
        if (checkInDate == null || checkOutDate == null) {
            return false;
        }
        return checkOutDate.isAfter(checkInDate);
    }
}
    