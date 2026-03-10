package com.hotel.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.inventory_service.dto.CreateRoomRequest;
import com.hotel.inventory_service.dto.updateRoomDTO;
import com.hotel.inventory_service.enums.RoomStatus;
import com.hotel.inventory_service.service.RoomService;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/inventory/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    //create room
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    // Endpoint to update the status of a room (e.g., from available to occupied or under maintenance)
    @PatchMapping("/{roomId}/status")
    public ResponseEntity<?> updateRoomStatus(@PathVariable Long roomId, @RequestParam RoomStatus newStatus) {
        return ResponseEntity.ok(roomService.updateStatus(roomId, newStatus));
    }
    
    // Endpoint to retrieve all rooms
    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }
    
    // Endpoint to retrieve available rooms
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRoomTypes() {
        return ResponseEntity.ok(roomService.getRoomsByStatus(RoomStatus.AVAILABLE));
    }

    // Endpoint to switch rooms from available to occupied
    @PatchMapping("/{roomId}/occupy")
    public ResponseEntity<?> occupyRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.updateStatus(roomId, RoomStatus.OCCUPIED));
    }

    // Endpoint to verify the availability of a room before making a reservation
    @GetMapping("/{idRoom}/validate")
    public ResponseEntity<?> validateRoom(@PathVariable Long idRoom) {
        return ResponseEntity.ok(roomService.validateRoom(idRoom));
    }

    // Endpoint to update room information
    @PatchMapping("/{roomId}/update")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId, @RequestBody updateRoomDTO request) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, request));
    }

    // Endpoint to retrieve a room by its ID
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }
    
}