package com.hotel.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.common_exception.ResourceNotFoundException;
import com.hotel.inventory_service.dto.CreateRoomRequest;
import com.hotel.inventory_service.dto.ResponseDto;
import com.hotel.inventory_service.dto.RoomDTO;
import com.hotel.inventory_service.dto.RoomValidationDTO;
import com.hotel.inventory_service.dto.updateRoomDTO;
import com.hotel.inventory_service.enums.RoomStatus;
import com.hotel.inventory_service.persistence.Room;
import com.hotel.inventory_service.repository.RoomRepository;
import com.hotel.inventory_service.repository.RoomTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    private final RoomTypeRepository roomTypeRepository;

    public ResponseDto createRoom(CreateRoomRequest request) {
        // Obtener el tipo de habitación utilizando el roomTypeId del request
        var roomType = roomTypeRepository.findById(request.roomTypeId()).orElseThrow(() -> new ResourceNotFoundException("El tipo habitacion con id: "+ request.roomTypeId() +" no existe"));

        // Crear una nueva instancia de Room utilizando el builder
        var room = Room.builder()
                .roomNumber(request.roomNumber())
                .roomType(roomType)
                .description(request.description())
                .build();
        roomRepository.save(room);
        return new ResponseDto("Room created successfully", room);
    }

    public ResponseDto updateStatus(Long roomId, RoomStatus newStatus) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("La habitacion con id: "+roomId +" no existe"));
        room.setStatus(newStatus);
        roomRepository.save(room);
        return new ResponseDto("Room status updated successfully", room);
    }
    

    //Get all rooms
    public List<Room> getAllRooms() {
        var rooms = roomRepository.findAll();
        return rooms;
    }

    // Get rooms by status
    public List<Room> getRoomsByStatus(RoomStatus status) {
        var rooms = roomRepository.findByStatus(status);
        return rooms;
    }

    // Validate room availability and price
    public RoomValidationDTO validateRoom(Long roomId) {
        return roomRepository.findById(roomId)
            .map(room -> {
                boolean available = room.getStatus() == RoomStatus.AVAILABLE;

                double currentPrice = room.getRoomType().getPricePerNight();

                return new RoomValidationDTO(room.getId(), currentPrice, available, available ? "Room is available" : "Room is not available");
            })
            .orElseGet(() -> new RoomValidationDTO(roomId, 0.0, false, "Room not found"));
    }

    // Update room( description)
    public ResponseDto updateRoom(Long roomId, updateRoomDTO request) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("La habitacion con id: "+roomId +" no existe"));
        
        if (request.description() != null) {
            room.setDescription(request.description());
        }

        if (request.roomTypeId() != null) {
            var roomType = roomTypeRepository.findById(request.roomTypeId()).orElseThrow(() -> new ResourceNotFoundException("Roomtype with id: "+request.roomTypeId() +" does not exist"));
            room.setRoomType(roomType);
        }

        roomRepository.save(room);
        return new ResponseDto("Room updated successfully", room);
    }

    public RoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("La habitacion con id: "+roomId +" no existe"));
        return new RoomDTO(room.getId(), room.getRoomNumber(), room.getDescription(), room.getStatus(), room.getRoomType().getPricePerNight());
    }
}
