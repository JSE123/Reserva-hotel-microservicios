package com.hotel.inventory_service.dto;

import com.hotel.inventory_service.enums.RoomStatus;

public record RoomDTO(Long id, String name, String description, RoomStatus status, Double pricePerNight) {
}
