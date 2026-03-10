package com.hotel.booking.dtos;

import com.hotel.booking.enums.RoomStatus;

public record RoomDTO(Long id, String name, String description, RoomStatus status, Double pricePerNight) {
    

}
