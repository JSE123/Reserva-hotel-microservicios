package com.hotel.inventory_service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRoomTypeRequest(
    @NotNull(message = "Name is required")
    String name,
    String description,
    @NotNull(message = "Max capacity is required")
    int maxCapacity
) {
} 
