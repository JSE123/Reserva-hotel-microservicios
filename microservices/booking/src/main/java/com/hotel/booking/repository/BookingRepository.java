package com.hotel.booking.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotel.booking.persistence.Booking;

import feign.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(Long userId);

    boolean existsByRoomIdAndCheckInLessThanAndCheckOutGreaterThan(
            Long roomId,
            LocalDate checkOut,
            LocalDate checkIn);
}
