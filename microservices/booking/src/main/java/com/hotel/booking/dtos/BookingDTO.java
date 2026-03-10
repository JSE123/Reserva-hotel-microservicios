package com.hotel.booking.dtos;

import com.hotel.booking.enums.BookingStatus;

public record BookingDTO(Long id, Long userId, Long roomId, Double totalAmount, BookingStatus status) {

}
