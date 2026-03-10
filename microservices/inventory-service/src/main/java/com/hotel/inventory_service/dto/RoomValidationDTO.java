package com.hotel.inventory_service.dto;

public record RoomValidationDTO(
    Long roomId,
    double pricePerNight,
    boolean available,
    String message
) {

}
