package com.hotel.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.inventory_service.enums.RoomStatus;
import com.hotel.inventory_service.persistence.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // Find rooms by status
        List<Room> findByStatus(RoomStatus status);
}
