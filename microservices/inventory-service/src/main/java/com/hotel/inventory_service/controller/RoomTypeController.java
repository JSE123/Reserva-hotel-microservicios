package com.hotel.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.inventory_service.dto.CreateRoomTypeRequest;
import com.hotel.inventory_service.dto.updatePricePerNightDTO;
import com.hotel.inventory_service.service.RoomTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory/api/v1/roomtypes")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;  

    // Endpoint to create a new room type
    @PostMapping
    public ResponseEntity<?> createRoomType(@Valid @RequestBody CreateRoomTypeRequest request) {
        var response = roomTypeService.createRoomTypee(request);
        return ResponseEntity.ok(response);  
    }

    // Endpoint to retrieve all room types
    @GetMapping("/all")
    public ResponseEntity<?> getAllRoomTypes() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }
   
    // Endpoint to updata price of a room type
    @PatchMapping("/{roomTypeId}/updatePrice")
    public ResponseEntity<?> updateRoomTypePrice(@PathVariable Long roomTypeId, @Valid @RequestBody updatePricePerNightDTO newPrice) {
        return ResponseEntity.ok(roomTypeService.updateRoomTypePrice(roomTypeId, newPrice.pricePerNight()));
    }

}
