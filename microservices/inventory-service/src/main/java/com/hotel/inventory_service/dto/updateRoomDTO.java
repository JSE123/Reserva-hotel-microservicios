package com.hotel.inventory_service.dto;

public record updateRoomDTO(
        String roomNumber,
        Long roomTypeId,
        String description) {

}
