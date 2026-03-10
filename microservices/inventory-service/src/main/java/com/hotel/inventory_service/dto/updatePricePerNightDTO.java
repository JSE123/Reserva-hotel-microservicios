package com.hotel.inventory_service.dto;

import jakarta.validation.constraints.NotNull;

public record updatePricePerNightDTO(
    @NotNull(message = "Price per night is required")
    Double pricePerNight
) {

}
