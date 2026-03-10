package com.hotel.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.inventory_service.dto.CreateRoomTypeRequest;
import com.hotel.inventory_service.dto.ResponseDto;
import com.hotel.inventory_service.persistence.RoomType;
import com.hotel.inventory_service.repository.RoomTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    public ResponseDto createRoomTypee(CreateRoomTypeRequest request) {
        // verificar que el tipo de habitacion no exista
        roomTypeRepository.findByName(request.name()).ifPresent(roomType -> {
            throw new IllegalArgumentException("Room type already exists");
        });

        // crear el tipo de habitacion
        RoomType roomType;
        
        roomType = RoomType.builder()
            .name(request.name())
            .description(request.description())
            .maxCapacity(request.maxCapacity())
            .build();

        //guedar el tipo de habitacion en la base de datos
        Object room = roomTypeRepository.save(roomType);


        return new ResponseDto("Room type created successfully", room);
    }

    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return roomTypes;
    }

    public ResponseDto updateRoomTypePrice(Long roomTypeId, Double newPrice) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new RuntimeException("Room type not found"));
        roomType.setPricePerNight(newPrice);
        roomTypeRepository.save(roomType);
        return new ResponseDto("Room type price updated successfully", roomType);
    }

}
