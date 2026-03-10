package com.hotel.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.booking.dtos.RoomDTO;
import com.hotel.booking.enums.RoomStatus;

@FeignClient(name = "inventory-service", url = "http://localhost:8085")
public interface RoomClient {

    @GetMapping("/inventory/api/v1/rooms/{idRoom}")
    RoomDTO getRoomById(@PathVariable Long idRoom);

    @GetMapping("/inventory/api/v1/rooms")
    RoomDTO getAllRooms();

    @PatchMapping("/inventory/api/v1/rooms/{roomId}/occupy")
    RoomDTO occupyRoom(@PathVariable Long roomId);

    @PatchMapping("/inventory/api/v1/rooms/{roomId}/status")
    RoomDTO releaseRoom(@PathVariable Long roomId, @RequestParam RoomStatus newStatus);
}
