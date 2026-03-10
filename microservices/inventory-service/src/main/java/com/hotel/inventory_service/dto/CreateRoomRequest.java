package com.hotel.inventory_service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
    @NotNull(message = "Room number is required")
    String roomNumber,
    @NotNull(message = "Room type ID is required")
    Long roomTypeId,
    String description
) {
}
